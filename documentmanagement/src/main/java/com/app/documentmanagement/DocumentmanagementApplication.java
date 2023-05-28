package com.app.documentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DocumentmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentmanagementApplication.class, args);
	}

}
