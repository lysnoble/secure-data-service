package org.slc.sli.ingestion.processors;

import java.util.Enumeration;
import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slc.sli.dal.TenantContext;
import org.slc.sli.dal.aspect.MongoTrackingAspect;
import org.slc.sli.ingestion.BatchJobStageType;
import org.slc.sli.ingestion.FaultType;
import org.slc.sli.ingestion.FaultsReport;
import org.slc.sli.ingestion.WorkNote;
import org.slc.sli.ingestion.landingzone.AttributeType;
import org.slc.sli.ingestion.landingzone.BatchJobAssembler;
import org.slc.sli.ingestion.landingzone.ControlFile;
import org.slc.sli.ingestion.landingzone.ControlFileDescriptor;
import org.slc.sli.ingestion.landingzone.IngestionFileEntry;
import org.slc.sli.ingestion.landingzone.validation.ControlFileValidator;
import org.slc.sli.ingestion.model.Error;
import org.slc.sli.ingestion.model.NewBatchJob;
import org.slc.sli.ingestion.model.ResourceEntry;
import org.slc.sli.ingestion.model.Stage;
import org.slc.sli.ingestion.model.da.BatchJobDAO;
import org.slc.sli.ingestion.queues.MessageType;
import org.slc.sli.ingestion.util.BatchJobUtils;
import org.slc.sli.ingestion.util.LogUtil;

/**
 * Control file processor.
 *
 * @author okrook
 *
 */
@Component
public class ControlFileProcessor implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(ControlFileProcessor.class);

    public static final BatchJobStageType BATCH_JOB_STAGE = BatchJobStageType.CONTROL_FILE_PROCESSOR;

    @Autowired
    private ControlFileValidator validator;

    @Autowired
    private BatchJobAssembler jobAssembler;

    @Autowired
    private BatchJobDAO batchJobDAO;

    @Override
    public void process(Exchange exchange) throws Exception {
        //We need to extract the TenantID for each thread, so the DAL has access to it.
//        try {
//            ControlFileDescriptor cfd = exchange.getIn().getBody(ControlFileDescriptor.class);
//            ControlFile cf = cfd.getFileItem();
//            String tenantId = cf.getConfigProperties().getProperty("tenantId");
//            TenantContext.setTenantId(tenantId);
//        } catch (NullPointerException ex) {
//            LOG.error("Could Not find Tenant ID.");
//            TenantContext.setTenantId(null);
//        }

        processUsingNewBatchJob(exchange);

        MongoTrackingAspect.aspectOf().reset();
    }

    private void processUsingNewBatchJob(Exchange exchange) throws Exception {

        String batchJobId = exchange.getIn().getHeader("BatchJobId", String.class);
        if (batchJobId == null) {

            handleNoBatchJobIdInExchange(exchange);
        } else {

            processControlFile(exchange, batchJobId);
        }
    }

    private void handleNoBatchJobIdInExchange(Exchange exchange) {
        exchange.getIn().setHeader("ErrorMessage", "No BatchJobId specified in exchange header.");
        exchange.getIn().setHeader("IngestionMessageType", MessageType.ERROR.name());
        LOG.error("Error:", "No BatchJobId specified in " + this.getClass().getName() + " exchange message header.");
    }

    private void processControlFile(Exchange exchange, String batchJobId) {
        Stage stage = Stage.createAndStartStage(BATCH_JOB_STAGE);

        NewBatchJob newJob = null;
        try {

            newJob = batchJobDAO.findBatchJobById(batchJobId);
            TenantContext.setTenantId(newJob.getTenantId());


            ControlFileDescriptor cfd = exchange.getIn().getBody(ControlFileDescriptor.class);

            ControlFile cf = cfd.getFileItem();

            newJob.setBatchProperties(aggregateBatchJobProperties(cf));

            FaultsReport errorReport = new FaultsReport();

            if (newJob.getProperty(AttributeType.PURGE.getName()) == null) {
            // TODO Deal with validator being autowired in BatchJobAssembler
                if (validator.isValid(cfd, errorReport)) {
                    createAndAddResourceEntries(newJob, cf);
                }
            }

            BatchJobUtils.writeErrorsWithDAO(batchJobId, cf.getFileName(), BATCH_JOB_STAGE, errorReport, batchJobDAO);

            // TODO set properties on the exchange based on job properties

            setExchangeHeaders(exchange, newJob, errorReport);

            setExchangeBody(exchange, batchJobId);

        } catch (Exception exception) {
            handleExceptions(exchange, batchJobId, exception);
        } finally {
            if (newJob != null) {
                BatchJobUtils.stopStageAndAddToJob(stage, newJob);
                batchJobDAO.saveBatchJob(newJob);
            }
        }
    }

    private void handleExceptions(Exchange exchange, String batchJobId, Exception exception) {
        exchange.getIn().setHeader("ErrorMessage", exception.toString());
        exchange.getIn().setHeader("IngestionMessageType", MessageType.ERROR.name());
        LogUtil.error(LOG, "Error processing batch job " + batchJobId, exception);
        if (batchJobId != null) {
            Error error = Error.createIngestionError(batchJobId, null, BATCH_JOB_STAGE.getName(), null, null, null,
                    FaultType.TYPE_ERROR.getName(), null, exception.toString());
            batchJobDAO.saveError(error);
        }
    }

    private void setExchangeHeaders(Exchange exchange, NewBatchJob newJob, FaultsReport errorReport) {
        if (errorReport.hasErrors()) {
            exchange.getIn().setHeader("hasErrors", errorReport.hasErrors());
            exchange.getIn().setHeader("IngestionMessageType", MessageType.ERROR.name());
        } else if (newJob.getProperty(AttributeType.PURGE.getName()) != null) {
            exchange.getIn().setHeader("IngestionMessageType", MessageType.PURGE.name());
        } else {
            exchange.getIn().setHeader("IngestionMessageType", MessageType.CONTROL_FILE_PROCESSED.name());
        }

        if (newJob.getProperty(AttributeType.DRYRUN.getName()) != null) {
            LOG.debug("Matched @dry-run tag from control file parsing.");
            exchange.getIn().setHeader(AttributeType.DRYRUN.getName(), true);
        } else {
            LOG.debug("Did not match @dry-run tag in control file.");
        }

        if (newJob.getProperty(AttributeType.NO_ID_REF.getName()) != null) {
            LOG.debug("Matched @no-id-ref tag from control file parsing.");
            exchange.getIn().setHeader(AttributeType.NO_ID_REF.name(), true);
        } else {
            LOG.debug("Did not match @no-id-ref tag in control file.");
        }
    }

    private void setExchangeBody(Exchange exchange, String batchJobId) {
        WorkNote workNote = WorkNote.createSimpleWorkNote(batchJobId);
        exchange.getIn().setBody(workNote, WorkNote.class);
    }

    private void createAndAddResourceEntries(NewBatchJob newJob, ControlFile cf) {
        for (IngestionFileEntry file : cf.getFileEntries()) {
            ResourceEntry resourceEntry = new ResourceEntry();
            resourceEntry.setResourceId(file.getFileName());
            resourceEntry.setExternallyUploadedResourceId(file.getFileName());
            resourceEntry.setResourceName(newJob.getSourceId() + file.getFileName());
            resourceEntry.setResourceFormat(file.getFileFormat().getCode());
            resourceEntry.setResourceType(file.getFileType().getName());
            resourceEntry.setChecksum(file.getChecksum());
            resourceEntry.setTopLevelLandingZonePath(newJob.getTopLevelSourceId());
            newJob.getResourceEntries().add(resourceEntry);
        }
    }

    private HashMap<String, String> aggregateBatchJobProperties(ControlFile cf) {
        HashMap<String, String> batchProperties = new HashMap<String, String>();
        Enumeration<Object> keys = cf.getConfigProperties().keys();
        Enumeration<Object> elements = cf.getConfigProperties().elements();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement().toString();
            String element = elements.nextElement().toString();
            batchProperties.put(key, element);
        }
        return batchProperties;
    }

    public BatchJobAssembler getJobAssembler() {
        return jobAssembler;
    }

    public void setJobAssembler(BatchJobAssembler jobAssembler) {
        this.jobAssembler = jobAssembler;
    }

}
