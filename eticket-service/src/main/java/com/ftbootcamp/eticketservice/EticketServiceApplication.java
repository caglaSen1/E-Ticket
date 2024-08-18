package com.ftbootcamp.eticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EticketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EticketServiceApplication.class, args);
	}

}
