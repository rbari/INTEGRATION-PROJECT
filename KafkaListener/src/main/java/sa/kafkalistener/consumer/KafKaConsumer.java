package sa.kafkalistener.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import sa.kafkalistener.data.CreateServiceData;
import sa.kafkalistener.data.GeneratedServiceDTO;
import sa.kafkalistener.producer.KafkaProducer;
import sa.kafkalistener.utils.AppConstants;

@Service
@AllArgsConstructor
public class KafKaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafKaConsumer.class);

    private KafkaProducer kafkaProducer;

    @KafkaListener(topics = AppConstants.CS_CREATION,
            groupId = AppConstants.GROUP_ID)
    public void createService(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CreateServiceData createServiceData = mapper.readValue(message, CreateServiceData.class);
        LOGGER.info(String.format("Message received -> %s", createServiceData));
        kafkaProducer.sendMessage(createServiceData, AppConstants.DSGS_CREATION);
    }

    @KafkaListener(topics = AppConstants.CS_START_SERVICE, groupId = AppConstants.GROUP_ID)
    public void startService(String message) throws JsonProcessingException {
        LOGGER.info(String.format("Message received -> %s", message));
        kafkaProducer.sendMessage(message, AppConstants.DSGS_START_SERVICE);
    }

    @KafkaListener(topics = AppConstants.CS_STOP_SERVICE, groupId = AppConstants.GROUP_ID)
    public void stopService(String message) throws JsonProcessingException {
        LOGGER.info(String.format("Message received -> %s", message));
        kafkaProducer.sendMessage(message, AppConstants.DSGS_STOP_SERVICE);
    }
}
