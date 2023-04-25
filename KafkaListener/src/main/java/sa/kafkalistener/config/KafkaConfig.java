package sa.kafkalistener.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import sa.kafkalistener.data.GeneratedServiceDTO;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, GeneratedServiceDTO> consumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put("bootstrap.servers", "alert-cricket-6290-us1-kafka.upstash.io:9092");
        props.put("sasl.mechanism", "SCRAM-SHA-256");
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"YWxlcnQtY3JpY2tldC02MjkwJI15DGb5T9i8SGIlnrntnj4LLY2sHmqBwcEfq14\" password=\"81ddca5211414c998e0f8ccf75ebe491\";");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "default");
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", JsonSerializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, GeneratedServiceDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, GeneratedServiceDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ProducerFactory<String, GeneratedServiceDTO> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put("bootstrap.servers", "alert-cricket-6290-us1-kafka.upstash.io:9092");
        configProps.put("sasl.mechanism", "SCRAM-SHA-256");
        configProps.put("security.protocol", "SASL_SSL");
        configProps.put("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"YWxlcnQtY3JpY2tldC02MjkwJI15DGb5T9i8SGIlnrntnj4LLY2sHmqBwcEfq14\" password=\"81ddca5211414c998e0f8ccf75ebe491\";");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put("key.serializer", StringSerializer.class);
        configProps.put("value.serializer", JsonSerializer.class);
        configProps.put("auto.offset.reset", "earliest");
        configProps.put("group.id", "default");
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, GeneratedServiceDTO> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
