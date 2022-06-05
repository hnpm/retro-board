package com.example.retroboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RetroBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetroBoardApplication.class, args);
	}

}
