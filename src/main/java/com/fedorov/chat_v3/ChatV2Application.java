package com.fedorov.chat_v3;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChatV2Application {

	public static void main(String[] args) {
		SpringApplication.run(ChatV2Application.class, args);
	}

	@Bean

	public ObjectMapper getObjectMapper() {

		return new ObjectMapper();

	}

}
