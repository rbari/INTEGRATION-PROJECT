package sa.kafkalistener.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import sa.kafkalistener.utils.AppConstants;

@Service
public class KafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(org.apache.kafka.clients.producer.KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message){
        kafkaTemplate.send(AppConstants.TOPIC_NAME, message);
        LOGGER.info(String.format("Message sent -> %s", message));
    }
}
