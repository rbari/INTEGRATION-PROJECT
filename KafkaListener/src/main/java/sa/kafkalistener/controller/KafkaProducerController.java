package sa.kafkalistener.controller;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.bind.annotation.*;
import sa.kafkalistener.producer.KafkaProducer;

import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/api/v1/kafka")
public class KafkaProducerController {

    private KafkaProducer kafkaProducer;

    public KafkaProducerController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }
    private AdminClient adminClient;

    @Autowired
    public KafkaProducerController(KafkaProperties kafkaProperties) {
        Properties props = new Properties();
        props.putAll(kafkaProperties.buildAdminProperties());
        this.adminClient = AdminClient.create(props);
    }

//    @GetMapping("/publish")
//    public ResponseEntity<String> publish(@RequestParam("message") String message){
//        kafkaProducer.sendMessage(message);
//        return ResponseEntity.ok("Message sent to kafka topic");
//    }

    @GetMapping("/api/addTopic/{topicName}")
    public ResponseEntity<String> addTopic(@PathVariable String topicName) {
        adminClient.createTopics(List.of(TopicBuilder.name(topicName)
                .replicas(1)
                .partitions(1)
                .build()));
        return ResponseEntity.ok(" Topic created successfully."+topicName);
    }
}
