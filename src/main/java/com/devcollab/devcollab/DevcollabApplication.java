package com.devcollab.devcollab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class DevcollabApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevcollabApplication.class, args);
	}

}
