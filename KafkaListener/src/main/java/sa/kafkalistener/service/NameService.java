package sa.kafkalistener.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sa.kafkalistener.consumer.KafKaConsumer;
import sa.kafkalistener.data.CreateServiceData;
import sa.kafkalistener.data.CreateServiceResponse;
import sa.kafkalistener.data.ServiceRunningData;
import sa.kafkalistener.producer.KafkaProducer;
import sa.kafkalistener.utils.AppConstants;

import java.util.HashSet;
import java.util.Set;

@Service
public class NameService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafKaConsumer.class);
    @Autowired
    private KafkaProducer kafkaProducer;
    private static Set<String> createdDSTopics = new HashSet<>();
    private static Set<String> createdCDSTopics = new HashSet<>();
    private static Set<String> createdSSTopics = new HashSet<>();
    private static Set<String> createdRSTopics = new HashSet<>();

    private static Set<String> runningDSTopics = new HashSet<>();
    private static Set<String> runningCDSTopics = new HashSet<>();
    private static Set<String> runningSSTopics = new HashSet<>();
    private static Set<String> runningRSTopics = new HashSet<>();

    public void generateNames(CreateServiceData createServiceData) throws JsonProcessingException {
        String ds = generateAndCreateDS(createServiceData.getTopicName(), createServiceData.getTopicInterval());
        String cds = generateAndCreateCDS(ds);
        generateAndCreateSS(cds);
        generateAndCreateRs(cds);

        createdCDSTopics.add(cds);
    }

    public void startServices(ServiceRunningData serviceRunningData) throws Exception {
        String ds = generateAndStartDs(serviceRunningData.getServiceName());
        String cds = generateAndStartCDS(ds);
        generateAndStartSS(cds);
        generateAndStartRs(cds);

        runningCDSTopics.add(cds);
    }

    private String generateAndStartDs(String ds) throws Exception {
        if(!isDSExist(ds))
            throw new Exception();

        sendToStartKafka(ds);
        runningDSTopics.add(ds);
        return ds;
    }

    private String generateAndCreateDS(String ds, long interval) throws JsonProcessingException {
        sendToCreationKafka(new CreateServiceResponse("DS", interval, Set.of(ds)));
        createdDSTopics.add(ds);
        return ds;
    }

    private String generateAndStartCDS(String ds) throws Exception {
        String cds = generateCDS(ds);
        if(!isCDSExist(cds))
            throw new Exception();

        sendToStartKafka(cds);

        return cds;
    }

    private String generateAndCreateCDS(String ds) throws JsonProcessingException {
        String cds = generateCDS(ds);
        sendToCreationKafka(new CreateServiceResponse("CDS", 0, Set.of(cds)));
        return cds;
    }

    private String generateCDS(String ds) {
        return "CDS" + ds.substring(ds.lastIndexOf("_"));
    }


    private void generateAndStartSS(String newCds) throws Exception {
        Set<String> ssSet = generateSSForStartStop(newCds, runningCDSTopics);
        for (String ss : ssSet) {
            sendToStartKafka(ss);
        }

        runningSSTopics.addAll(ssSet);
    }

    private void generateAndCreateSS(String newCds) throws JsonProcessingException {
        Set<String> ssSet = generateSS(newCds, createdCDSTopics);
        for (String ss : ssSet) {
            sendToCreationKafka(new CreateServiceResponse("SS", 0, Set.of(ss)));
        }

        createdSSTopics.addAll(ssSet);
    }

    private Set<String> generateSS(String newCds, Set<String> CDSs) throws JsonProcessingException {

        Set<String> ssSet = new HashSet<>();
            for (String s : CDSs) {
                ssSet.add("SS" + s.substring(s.lastIndexOf("_")) + newCds.substring(newCds.lastIndexOf("_")));
            }

        return ssSet;
    }

    private Set<String> generateSSForStartStop(String newCds, Set<String> CDSs) throws Exception {

        Set<String> ssSet = new HashSet<>();
        for (String s : CDSs) {
            String a = s.substring(s.lastIndexOf("_") + 1);
            String b = newCds.substring(newCds.lastIndexOf("_") + 1);
            if (isSSExist("SS_"+a+"_"+b)){
                ssSet.add("SS_"+a+"_"+b);
            } else if (isSSExist("SS_"+b+"_"+a)) {
                ssSet.add("SS_"+b+"_"+a);
            } else {
                throw new Exception();
            }
        }

        return ssSet;
    }

    private void generateAndStartRs(String newCds) throws Exception {
        Set<String> rsSet = generateRsForStartStop(newCds, runningCDSTopics);

        for (String rs : rsSet) {
            sendToStartKafka(rs);
        }
        runningRSTopics.addAll(rsSet);
    }

    private void generateAndCreateRs(String newCds) throws JsonProcessingException {
        Set<String> rsSet = generateRs(newCds, createdCDSTopics);
        for (String rs : rsSet) {
            sendToCreationKafka(new CreateServiceResponse("RS", 0, Set.of(rs)));
        }

        createdRSTopics.addAll(rsSet);
    }

    private Set<String> generateRsForStartStop(String newCds, Set<String> CDSs) throws Exception {
        Set<String> rsNames = new HashSet<>();

        for (String s : CDSs) {
            String a = s.substring(s.lastIndexOf("_") + 1);
            String b = newCds.substring(newCds.lastIndexOf("_") + 1);
            if (isRSExist("RS_"+a+"_"+b)){
                rsNames.add("RS_"+a+"_"+b);
            } else if (isRSExist("RS_"+b+"_"+a)){
                rsNames.add("RS_"+b+"_"+a);
            } else {
                throw new Exception();
            }
        }

        return rsNames;
    }

    private Set<String> generateRs(String newCds, Set<String> CDSs) throws JsonProcessingException {
        Set<String> rsNames = new HashSet<>();

        for (String s : CDSs) {
            rsNames.add("RS" + s.substring(s.lastIndexOf("_")) + newCds.substring(newCds.lastIndexOf("_")));
        }

        return rsNames;
    }


    private void sendToCreationKafka(CreateServiceResponse createServiceResponse) throws JsonProcessingException {
        LOGGER.info(String.format("Message sent -> %s to %s", createServiceResponse, AppConstants.DSGS_CREATION));
        kafkaProducer.sendMessage(createServiceResponse, AppConstants.DSGS_CREATION);
    }

    private void sendToStartKafka(String name) throws JsonProcessingException {
        LOGGER.info(String.format("Message sent -> %s to %s", name, AppConstants.DSGS_START_SERVICE));
        kafkaProducer.sendMessage(name, AppConstants.DSGS_START_SERVICE);
    }

    private void sendToStopKafka(String name) throws JsonProcessingException {
        LOGGER.info(String.format("Message sent -> %s to %s", name, AppConstants.DSGS_STOP_SERVICE));
        kafkaProducer.sendMessage(name, AppConstants.DSGS_STOP_SERVICE);
    }

    private boolean isDSExist(String ds) {
        return createdDSTopics.contains(ds);
    }

    private boolean isCDSExist(String cds) {
        return createdCDSTopics.contains(cds);
    }

    private boolean isSSExist(String ss) {
        return createdSSTopics.contains(ss);
    }

    private boolean isRSExist(String rs) {
        return createdRSTopics.contains(rs);
    }
}
