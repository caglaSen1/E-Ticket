package com.ftbootcamp.eticketauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EticketAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EticketAuthServiceApplication.class, args);
    }

}
