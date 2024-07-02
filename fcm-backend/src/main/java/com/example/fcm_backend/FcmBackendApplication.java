package com.example.fcm_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class FcmBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FcmBackendApplication.class, args);
	}

}
