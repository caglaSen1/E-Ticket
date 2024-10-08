package com.ftbootcamp.eticketindexservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableElasticsearchRepositories
public class EticketIndexServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EticketIndexServiceApplication.class, args);
	}

}
