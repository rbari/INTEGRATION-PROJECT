package zipService.zipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Sender {
    @Autowired
   private KafkaTemplate<String, Object> kafkaTemplate;
	

    public void send(String topic, RequestWrapper rq) throws JsonProcessingException{
    	
         ObjectMapper mapper = new ObjectMapper();

         String jsonInString = mapper.writeValueAsString(rq);

         kafkaTemplate.send(topic, jsonInString);
    	
    }
}