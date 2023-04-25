package sa.kafkalistener.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import sa.kafkalistener.data.GeneratedServiceDTO;
import sa.kafkalistener.utils.AppConstants;

@Service
public class KafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(org.apache.kafka.clients.producer.KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, GeneratedServiceDTO> kafkaTemplate;

    public void sendMessage(GeneratedServiceDTO generatedServiceDTO){
        kafkaTemplate.send(AppConstants.PUSH_TOPIC_NAME, generatedServiceDTO);
        LOGGER.info(String.format("Message sent -> %s", generatedServiceDTO));
    }
}
