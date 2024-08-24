package com.ftbootcamp.eticketservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI eticketServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Eticket Service")
                        .description("Trip and Ticket Operations")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Eticket Service Project Documentation")
                        .url("https://springdoc.org/"));
    }
}