package com.example.catsanddogstelegram;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@OpenAPIDefinition
@SpringBootApplication
@EnableCaching
public class CatsAndDogsTelegramApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatsAndDogsTelegramApplication.class, args);
    }

}
