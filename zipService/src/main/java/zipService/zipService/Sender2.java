package zipService.zipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Service
public class Sender2 {
    @Autowired
   private KafkaTemplate<String, Object> kafkaTemplate;
//	@Autowired
//	ZipController zipController;
	

    public void send(String topic, RequestWrapper rq) throws JsonProcessingException{
    	
         ObjectMapper mapper = new ObjectMapper();

         String jsonInString = mapper.writeValueAsString(rq);

         kafkaTemplate.send(topic, jsonInString);
    	
    }
//    public void send2(String topic, RequestWrapper rq) throws JsonProcessingException{
//    	
//    	ObjectMapper mapper = new ObjectMapper();
//    	
//    	String jsonInString = mapper.writeValueAsString(rq);
//    	
//    	kafkaTemplate.send(topic, jsonInString);
//    	
//    }
    
//	  @KafkaListener(topics = "tester", groupId = "default")
//	public void receive(String message) {
//		  
//		  try {
//	            ObjectMapper mapper = new ObjectMapper();
//	            RequestWrapper requestWrapper = mapper.readValue(message, RequestWrapper.class);
//
//	            System.out.println("Receiving");
//	            System.out.println(requestWrapper);
//	    		zipController.downloadZip(requestWrapper.getServiceName());
//
//	        } catch (JsonProcessingException e) {
//	            throw new RuntimeException(e);
//	        }
//		System.out.println("hi every body");
//		zipController.downloadZip(serviceName);
		
//	}
//    public void send2(String topic){
//    	kafkaTemplate.send(topic);
//    	
//    }
}