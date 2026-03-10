package com.example.backoffice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication(scanBasePackages = {
	    "com.example.backoffice", 
	    "com.example.ads",
	    "com.example.user"
	})
	@EntityScan({
	    "com.example.ads.model",
	    "com.example.user.model"
	})
	@EnableJpaRepositories({
	    "com.example.ads.repository",
	    "com.example.user.repository"
	})
public class BackOfficeApplication {
	 public static void main(String[] args) {
	        SpringApplication.run(BackOfficeApplication.class, args);
	   }
}
