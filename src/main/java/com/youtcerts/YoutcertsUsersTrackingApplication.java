package com.youtcerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.youtcerts")
public class YoutcertsUsersTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoutcertsUsersTrackingApplication.class, args);
	}

}
