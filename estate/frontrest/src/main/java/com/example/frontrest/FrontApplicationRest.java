package com.example.frontrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "com.example.frontrest", 
    "com.example.ads"
})
@EntityScan({
    "com.example.ads.model"
})
@EnableJpaRepositories({
    "com.example.ads.repository"
})
public class FrontApplicationRest {
    public static void main(String[] args) {
        SpringApplication.run(FrontApplicationRest.class, args);
    }
}