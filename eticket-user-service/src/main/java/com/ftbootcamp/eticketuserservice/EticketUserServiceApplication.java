package com.ftbootcamp.eticketuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EticketUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EticketUserServiceApplication.class, args);
    }

}
