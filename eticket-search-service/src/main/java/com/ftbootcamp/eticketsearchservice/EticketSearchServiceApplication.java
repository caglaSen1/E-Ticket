package com.ftbootcamp.eticketsearchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableDiscoveryClient
@EnableElasticsearchRepositories
@SpringBootApplication
public class EticketSearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EticketSearchServiceApplication.class, args);
    }

}
