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

package org.slc.sli.ingestion.transformation.normalization.did;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.slc.sli.common.domain.EmbeddedDocumentRelations;
import org.slc.sli.common.domain.NaturalKeyDescriptor;
import org.slc.sli.common.util.uuid.UUIDGeneratorStrategy;
import org.slc.sli.domain.Entity;
import org.slc.sli.ingestion.transformation.normalization.IdNormalizerFlag;
import org.slc.sli.ingestion.transformation.normalization.IdResolutionException;
import org.slc.sli.ingestion.validation.ErrorReport;
import org.slc.sli.validation.SchemaRepository;
import org.slc.sli.validation.schema.NeutralSchema;

/**
 * Resolver for deterministic id resolution.
 *
 * @author jtully
 * @author vmcglaughlin
 *
 */
public class DeterministicIdResolver {

    @Autowired
    @Qualifier("deterministicUUIDGeneratorStrategy")
    private UUIDGeneratorStrategy uuidGeneratorStrategy;

    private DidSchemaParser didSchemaParser;

    @Autowired
    private DidEntityConfigReader didConfigReader;

    @Autowired
    private SchemaRepository schemaRepository;

    private static final Logger LOG = LoggerFactory.getLogger(DeterministicIdResolver.class);

    private static final String BODY_PATH = "body.";
    private static final String PATH_SEPARATOR = "\\.";

    public void resolveInternalIds(Entity entity, String tenantId, ErrorReport errorReport) {

        if (IdNormalizerFlag.useOldNormalization) {
            // TODO: remove IdNormalizerFlag
            LOG.trace("Use old Id Normalizer");
            return;
        }

        DidEntityConfig entityConfig = getEntityConfig(entity.getType());

        if (entityConfig == null) {
            return;
        }

        if (entityConfig.getReferenceSources() == null || entityConfig.getReferenceSources().isEmpty()) {
            LOG.debug("Entity configuration contains no references --> returning...");
            return;
        }

        String collectionName = "";
        String referenceEntityType = "";
        String sourceRefPath = "";

        for (DidRefSource didRefSource : entityConfig.getReferenceSources()) {
            try {
                referenceEntityType = didRefSource.getEntityType();

                sourceRefPath = didRefSource.getSourceRefPath();

                NeutralSchema schema = schemaRepository.getSchema(referenceEntityType);
                if (schema != null && schema.getAppInfo() != null) {
                    collectionName = schema.getAppInfo().getCollectionType();
                }

                handleDeterministicIdForReference(entity, didRefSource, collectionName, tenantId);

            } catch (IdResolutionException e) {
                handleException(sourceRefPath, entity.getType(), referenceEntityType, e, errorReport);
            }
        }
    }

    private DidEntityConfig getEntityConfig(String entityType) {
        //use the json config if there is one
        DidEntityConfig entityConfig = didConfigReader.getDidEntityConfiguration(entityType);

        if (entityConfig == null) {
            entityConfig = didSchemaParser.getEntityConfigs().get(entityType);
        }

        return entityConfig;
    }

    private DidRefConfig getRefConfig(String refType) {
        return didSchemaParser.getRefConfigs().get(refType);
    }

    private void handleDeterministicIdForReference(Entity entity, DidRefSource didRefSource, String collectionName,
            String tenantId) throws IdResolutionException {

        String entityType = didRefSource.getEntityType();
        String sourceRefPath = didRefSource.getSourceRefPath();

        LOG.trace("Handling DID for reference {}", entityType);

        DidRefConfig didRefConfig = getRefConfig(entityType);

        if (didRefConfig == null) {
            return;
        }

        //handle case of references within embedded lists of objects (for assessments)
        //split source ref path and look for lists in embedded objects
        String strippedRefPath = sourceRefPath.replaceFirst(BODY_PATH, "");
        String[] pathParts = strippedRefPath.split(PATH_SEPARATOR);
        String refObjName = pathParts[pathParts.length-1];

        //get a list of the parentNodes
        List<Map<String, Object>> parentNodes = new ArrayList<Map<String, Object>>();
        extractReferenceParentNodes(parentNodes, entity.getBody(), pathParts, 0);

        //resolve and set all the parentNodes
        LOG.trace("Resolving parentNodes references");
        for (Map<String, Object> node : parentNodes) {
            Object resolvedRef = resolveReference(node.get(refObjName), didRefSource.isOptional(), entity, didRefConfig, collectionName, tenantId);
            if (resolvedRef != null) {
                node.put(refObjName, resolvedRef);
            }
        }
    }

    /**
     * Recursive extraction of all parent nodes in the entity that contain the reference
     * @throws IdResolutionException
     */
    @SuppressWarnings("unchecked")
    private void extractReferenceParentNodes(List<Map<String, Object>> parentNodes, Map<String, Object> curNode, String[] pathParts, int level) throws IdResolutionException {
        String nextNodeName = pathParts[level];
        if (level >= pathParts.length-1) {
            parentNodes.add(curNode);
        } else {
            Object nextNode = curNode.get(nextNodeName);
            if (nextNode instanceof List) {
                List<Object> nodeList = (List<Object>) nextNode;
                for (Object nodeObj : nodeList) {
                    if (nodeObj instanceof Map) {
                        extractReferenceParentNodes(parentNodes, (Map<String, Object>) nodeObj, pathParts, level+1);
                    }
                }
            } else if (nextNode instanceof Map) {
                extractReferenceParentNodes(parentNodes, (Map<String, Object>) nextNode, pathParts, level+1);
            }
        }
    }

    private Object resolveReference(Object referenceObject, boolean isOptional, Entity entity, DidRefConfig didRefConfig,
            String collectionName, String tenantId) throws IdResolutionException {

        LOG.trace("Resolving reference");

        String refType = didRefConfig.getEntityType();

        if (referenceObject == null) {
            // ignore an empty reference if it is optional
            if (isOptional) {
                return null;
            } else {
                throw new IdResolutionException("Missing required reference", refType, null);
            }
        }

        if (referenceObject instanceof List) {
            // handle a list of reference objects
            @SuppressWarnings("unchecked")
            List<Object> refList = (List<Object>) referenceObject;
            List<String> uuidList = new ArrayList<String>();

            for (Object reference : refList) {
                @SuppressWarnings("unchecked")
                String uuid = getId((Map<String, Object>) reference, tenantId, didRefConfig);
                if (uuid != null && !uuid.isEmpty()) {
                    uuidList.add(uuid);
                } else {
                    throw new IdResolutionException("Null or empty deterministic id generated", refType, uuid);
                }
            }
            return uuidList;
        } else if (referenceObject instanceof Map) {
            // handle a single reference object
            @SuppressWarnings("unchecked")
            Map<String, Object> reference = (Map<String, Object>) referenceObject;

            String uuid = getId(reference, tenantId, didRefConfig);
            if (uuid != null && !uuid.isEmpty()) {
                return uuid;
            } else {
                throw new IdResolutionException("Null or empty deterministic id generated", refType, uuid);
            }
        } else {
            throw new IdResolutionException("Unsupported reference object type", refType, null);
        }
    }

    private Object getProperty(Object bean, String sourceRefPath) throws IdResolutionException {
        Object referenceObject;
        try {
            referenceObject = PropertyUtils.getProperty(bean, sourceRefPath);
        } catch (IllegalArgumentException e) {
            throw new IdResolutionException("Unable to pull reference object from entity", sourceRefPath, null, e);
        } catch (IllegalAccessException e) {
            throw new IdResolutionException("Unable to pull reference object from entity", sourceRefPath, null, e);
        } catch (InvocationTargetException e) {
            throw new IdResolutionException("Unable to pull reference object from entity", sourceRefPath, null, e);
        } catch (NoSuchMethodException e) {
            throw new IdResolutionException("Unable to pull reference object from entity", sourceRefPath, null, e);
        }

        return referenceObject;
    }

    private void setProperty(Object bean, String fieldPath, Object uuid) throws IdResolutionException {
        try {
            PropertyUtils.setProperty(bean, fieldPath, uuid);
        } catch (IllegalAccessException e) {
            throw new IdResolutionException("Unable to set reference object for entity", fieldPath, uuid.toString(), e);
        } catch (InvocationTargetException e) {
            throw new IdResolutionException("Unable to set reference object for entity", fieldPath, uuid.toString(), e);
        } catch (NoSuchMethodException e) {
            throw new IdResolutionException("Unable to set reference object for entity", fieldPath, uuid.toString(), e);
        }
    }

    private void handleException(String sourceRefPath, String entityType, String referenceType, Exception e,
            ErrorReport errorReport) {
        LOG.error("Error accessing indexed bean property " + sourceRefPath + " for bean " + entityType, e);
        String errorMessage = "ERROR: Failed to resolve a deterministic id" + "\n       Entity " + entityType
                + ": Reference to " + referenceType
                + " is incomplete because the following reference field is not resolved: " + sourceRefPath;

        errorReport.error(errorMessage, this);
    }

    // function which, given reference type map (source object) and refConfig, return a did
    private String getId(Map<String, Object> reference, String tenantId, DidRefConfig didRefConfig)
            throws IdResolutionException {
        LOG.trace("Getting deterministic id");
        if (didRefConfig.getEntityType() == null || didRefConfig.getEntityType().isEmpty()) {
            return null;
        }

        if (didRefConfig.getKeyFields() == null || didRefConfig.getKeyFields().isEmpty()) {
            return null;
        }

        Map<String, String> naturalKeys = new HashMap<String, String>();

        for (KeyFieldDef keyFieldDef : didRefConfig.getKeyFields()) {
            // populate naturalKeys
            Object value = null;
            if (keyFieldDef.getRefConfig() != null) {
                Object nestedRef = getProperty(reference, keyFieldDef.getValueSource());

                if (nestedRef == null) {
                    if (keyFieldDef.isOptional() == false) {
                        throw new IdResolutionException("No value found for required reference",
                                keyFieldDef.getValueSource(), "");
                    } else {
                        // since it's an optional field, replace it with "" in the natural key list
                        value = "";
                    }
                    // otherwise, continue to end of loop with null 'value'
                } else {
                    if (nestedRef instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> nestedRefMap = (Map<String, Object>) nestedRef;
                        value = getId(nestedRefMap, tenantId, keyFieldDef.getRefConfig());
                    } else {
                        throw new IdResolutionException("Non-map value found from entity",
                                keyFieldDef.getValueSource(), "");
                    }
                }

            } else {
                value = getProperty(reference, keyFieldDef.getValueSource());
            }

            String fieldName = keyFieldDef.getKeyFieldName();
            // don't add null or empty keys to the naturalKeys map
            if (fieldName != null && !fieldName.isEmpty() && (value != null || keyFieldDef.isOptional())) {
                naturalKeys.put(fieldName, value == null ? "" : value.toString());
            } else {
                //
            }
        }

        // no natural keys found
        if (naturalKeys.isEmpty()) {
            return null;
        }

        // TODO: need to verify this
        String parentId = null;
        String entityType = didRefConfig.getEntityType();
        if (EmbeddedDocumentRelations.getSubDocuments().contains(entityType)) {
            String parentKey = EmbeddedDocumentRelations.getParentFieldReference(entityType);
            parentId = naturalKeys.get(parentKey);
        }

        NaturalKeyDescriptor naturalKeyDescriptor = new NaturalKeyDescriptor(naturalKeys, tenantId,
                didRefConfig.getEntityType(), parentId);
        return uuidGeneratorStrategy.generateId(naturalKeyDescriptor);
    }

    public DidSchemaParser getDidSchemaParser() {
        return didSchemaParser;
    }

    public void setDidSchemaParser(DidSchemaParser didSchemaParser) {
        this.didSchemaParser = didSchemaParser;
    }

}
