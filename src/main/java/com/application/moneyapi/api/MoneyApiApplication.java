package com.application.moneyapi.api;

import com.application.moneyapi.api.config.property.ApplicationApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationApiProperty.class)
public class MoneyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyApiApplication.class, args);
    }

}
