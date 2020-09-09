package com.fest.pecfestBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan({"com.fest.pecfestBackend"})
@EnableAsync
public class PecfestBackendApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PecfestBackendApplication.class, args);
	}
	
}
