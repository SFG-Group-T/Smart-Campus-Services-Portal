package com.groupT.Smart.Campus.Services.Portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class SmartCampusServicesPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartCampusServicesPortalApplication.class, args);
	}

}
