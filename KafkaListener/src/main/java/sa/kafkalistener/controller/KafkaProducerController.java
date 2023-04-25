package sa.kafkalistener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.kafkalistener.data.GeneratedServiceDTO;
import sa.kafkalistener.producer.KafkaProducer;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/kafka")
public class KafkaProducerController {

    @Autowired
    private KafkaProducer kafkaProducer;


    @GetMapping("/publish")
    public ResponseEntity<String> publish(){
        kafkaProducer.sendMessage(new GeneratedServiceDTO("hello", Collections.emptySet()));
        return ResponseEntity.ok("Message sent to kafka topic");
    }

}
