package UnzipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Sender {
    @Autowired
    private KafkaTemplate<String, RequestWrapper> kafkaTemplate;

    public void send(String topic, RequestWrapper requestWrapper){
        kafkaTemplate.send(topic, requestWrapper);
    }
}
