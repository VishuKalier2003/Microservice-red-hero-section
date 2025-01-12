package site.webred;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WebredApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebredApplication.class, args);
	}
}
