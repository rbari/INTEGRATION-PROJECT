package UnzipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@EnableKafka
public class UnzipServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(UnzipServiceApplication.class, args);
	}

//	@Autowired
//	private Sender sender;

	@Override
	public void run(String... args) throws Exception {
//		RequestWrapper responseWrapper = new RequestWrapper("test path","test Service Namee");
//		sender.send("fileunziped",responseWrapper);
	}
}
