/*
 * Copyright 2012 Shared Learning Collaborative, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.slc.sli.search.process.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQBytesMessage;
import org.apache.activemq.util.ByteSequence;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slc.sli.search.entity.IndexEntity;
import org.slc.sli.search.process.IncrementalLoader;
import org.slc.sli.search.process.Indexer;
import org.slc.sli.search.transform.IndexEntityConverter;
import org.slc.sli.search.util.NestedMapUtil;
import org.slc.sli.search.util.amq.JMSQueueConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listens to ActiveMQ for Sarje messages. Filters the message and passes
 * data to the Indexer to be indexed in elasticsearch.
 * 
 * @author dwu
 * 
 */
public class IncrementalListenerImpl implements IncrementalLoader {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Indexer indexer;

    private IndexEntityConverter indexEntityConverter;

    private ObjectMapper mapper = new ObjectMapper();

    private JMSQueueConsumer activeMQConsumer;

    public void init() throws Exception {

        listen();
    }

    public void destroy() throws Exception {
        this.activeMQConsumer.destroy();

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.slc.sli.search.process.impl.IncrementalListener#listen()
     */
    public void listen() throws Exception {
        this.activeMQConsumer.getConsumer().setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                process(message);
            }
        });
    }

    /**
     * Process a message from the queue
     * 
     * @param opLog
     */
    public void process(Message message) {
        if (message != null) {
            try {
                String opLog = "";
                // for now we will always receive ActiveMQBytesMessage.
                // we also support TextMessage if it ever needs to be used.
                if (message instanceof ActiveMQBytesMessage) {
                    ActiveMQBytesMessage byteMessage = (ActiveMQBytesMessage) message;
                    ByteSequence bs = byteMessage.getContent();
                    InputStream is = new ByteArrayInputStream(bs.getData());
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String line = "";
                    StringBuilder sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    opLog = sb.toString();

                } else if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    opLog = textMessage.getText();
                }

                IndexEntity entity = convertToEntity(opLog);
                if (entity != null) {
                    sendToIndexer(entity);
                }
            } catch (Exception e) {
                logger.error("Error processing message", e);
            }
        }
    }

    /**
     * Convert oplog message to an IndexEntity, based on the action type (insert, update, delete)
     * 
     * @param opLog
     * @return
     * @throws Exception
     */
    public IndexEntity convertToEntity(String opLog) throws Exception {

        IndexEntity entity = null;
        opLog = preProcess(opLog);

        // check action type and convert to an index entity
        if (isInsert(opLog)) {
            entity = convertInsertToEntity(opLog);
        } else if (isUpdate(opLog)) {
            entity = convertUpdateToEntity(opLog);
        } else if (isDelete(opLog)) {
            entity = convertDeleteToEntity(opLog);
        } else {
            logger.info("Unrecognized message type. Ignoring.");
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
    private IndexEntity convertInsertToEntity(String opLog) throws Exception {

        // parse out entity
        List<Map<String, Object>> opLogs = mapper.readValue(opLog, new TypeReference<List<Map<String, Object>>>() {
        });
        if (opLogs.size() == 0) {
            return null;
        }
        Map<String, Object> opLogMap = opLogs.get(0);
        Map<String, Object> entityMap = (Map<String, Object>) opLogMap.get("o");
        // convert to index entity object
        return indexEntityConverter.fromEntity(getMeta(opLogMap).getIndex(), IndexEntity.Action.INDEX, entityMap);
    }

    @SuppressWarnings("unchecked")
    private IndexEntity convertUpdateToEntity(String opLog) throws Exception {

        // parse out entity data
        List<Map<String, Object>> opLogs = mapper.readValue(opLog, new TypeReference<List<Map<String, Object>>>() {
        });
        if (opLogs.size() == 0) {
            return null;
        }
        Map<String, Object> opLogMap = opLogs.get(0);

        Map<String, Object> o2 = (Map<String, Object>) opLogMap.get("o2");
        String id = (String) o2.get("_id");
        Meta meta = getMeta(opLogMap);
        String type = meta.getType();
        Map<String, Object> metadata = (Map<String, Object>) o2.get("metaData");
        Map<String, Object> o = (Map<String, Object>) opLogMap.get("o");
        Map<String, Object> updates = (Map<String, Object>) o.get("$set");

        // merge data into entity json (id, type, metadata.tenantId, body)
        Map<String, Object> entityMap = new HashMap<String, Object>();
        entityMap.put("_id", id);
        entityMap.put("type", type);
        entityMap.put("metaData", metadata);

        for (String updateField : updates.keySet()) {
            List<String> fieldChain = NestedMapUtil.getPathLinkFromDotNotation(updateField);
            NestedMapUtil.put(fieldChain, updates.get(updateField), entityMap);
        }

        // convert to index entity object
        return indexEntityConverter.fromEntity(meta.getIndex(), IndexEntity.Action.UPDATE, entityMap);
    }

    @SuppressWarnings("unchecked")
    private IndexEntity convertDeleteToEntity(String opLog) throws Exception {

        // parse out entity data
        List<Map<String, Object>> opLogs = mapper.readValue(opLog, new TypeReference<List<Map<String, Object>>>() {
        });
        if (opLogs.size() == 0) {
            return null;
        }
        Map<String, Object> opLogMap = opLogs.get(0);
        Map<String, Object> o = (Map<String, Object>) opLogMap.get("o");
        String id = (String) o.get("_id");
        ;

        Meta meta = getMeta(opLogMap);
        String type = meta.getType();

        // merge data into entity json (id, type, metadata.tenantId)
        Map<String, Object> entityMap = new HashMap<String, Object>();
        entityMap.put("_id", id);
        entityMap.put("type", type);
        // entityMap.put("metaData", metadata);

        // convert to index entity object
        return indexEntityConverter.fromEntity(meta.getIndex(), IndexEntity.Action.DELETE, entityMap);
    }

    private Meta getMeta(Map<String, Object> opLogMap) {
        String[] meta = ((String) opLogMap.get("ns")).split("\\.");
        return new Meta(meta[0], meta[1]);
    }

    // TODO : is there a better way to make the json valid?
    private String preProcess(String entityStr) {

        // handle ISODates and NumberLong
        entityStr = entityStr.replaceAll("ISODate\\((\".*?\")\\)", "$1");
        entityStr = entityStr.replaceAll("NumberLong\\((.*?)\\)", "$1");
        return entityStr;
    }

    private boolean isInsert(String opLog) {
        return opLog.contains("\"op\":\"i\"");
    }

    private boolean isUpdate(String opLog) {
        return opLog.contains("\"op\":\"u\"");
    }

    private boolean isDelete(String opLog) {
        return opLog.contains("\"op\":\"d\"");
    }

    private void sendToIndexer(IndexEntity entity) {
        indexer.index(entity);
    }

    public void setIndexer(Indexer indexer) {
        this.indexer = indexer;
    }

    public void setIndexEntityConverter(IndexEntityConverter indexEntityConverter) {
        this.indexEntityConverter = indexEntityConverter;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void setActiveMQConsumer(JMSQueueConsumer activeMQConsumer) {
        this.activeMQConsumer = activeMQConsumer;
    }

    private static class Meta {
        private final String index;
        private final String type;

        public Meta(String index, String type) {
            super();
            this.index = index;
            this.type = type;
        }

        public String getIndex() {
            return index;
        }

        public String getType() {
            return type;
        }
    }

    public String getHealth() {
        return getClass() + "{}";
    }

}