package com.example.catsanddogstelegram;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class CatsAndDogsTelegramApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatsAndDogsTelegramApplication.class, args);
    }

}
