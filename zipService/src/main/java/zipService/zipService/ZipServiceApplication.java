package zipService.zipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableFeignClients
@EnableKafka
public class ZipServiceApplication {
	
	
	
	public static void main(String[] args) {
	
		SpringApplication.run(ZipServiceApplication.class, args);
		
		
	
	}
	
//	@Autowired
//	private Sender sender;
//	@Override
//	public void run(String... strings) throws Exception {
//		RequestWrapper rq = new RequestWrapper("najkaj", "najkaj");
//		sender.send("tester", rq);
//	}

}
