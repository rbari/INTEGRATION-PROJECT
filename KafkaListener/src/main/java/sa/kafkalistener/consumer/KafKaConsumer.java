package sa.kafkalistener.consumer;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import sa.kafkalistener.data.GeneratedServiceDTO;
import sa.kafkalistener.producer.KafkaProducer;
import sa.kafkalistener.utils.AppConstants;

@Service
@AllArgsConstructor
public class KafKaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafKaConsumer.class);

    private KafkaProducer kafkaProducer;

    @KafkaListener(topics = AppConstants.LIST_TOPIC_NAME,
            groupId = AppConstants.GROUP_ID)
    public void consume(GeneratedServiceDTO serviceDTO){
        kafkaProducer.sendMessage(serviceDTO);
        LOGGER.info(String.format("Message received -> %s", serviceDTO));
    }
}
