package dev.envycodes.workshop;

import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AiWorkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiWorkshopApplication.class, args);
	}

	@Bean
	SimpleLoggerAdvisor simpleLoggerAdvisor(){
		return new SimpleLoggerAdvisor();
	}

}
