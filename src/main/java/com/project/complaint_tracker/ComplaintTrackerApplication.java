package com.project.complaint_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
public class ComplaintTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComplaintTrackerApplication.class, args);
	}

}
