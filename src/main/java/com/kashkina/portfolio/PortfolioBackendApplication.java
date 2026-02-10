package com.kashkina.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Collections;

@SpringBootApplication
@EnableAsync
public class PortfolioBackendApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PortfolioBackendApplication.class);
		app.setDefaultProperties(
				Collections.singletonMap("server.use-forward-headers", "true")
		);
		app.run(args);
	}

}
