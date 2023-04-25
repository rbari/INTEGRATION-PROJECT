package UnzipService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class UnzipServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnzipServiceApplication.class, args);
	}

}
