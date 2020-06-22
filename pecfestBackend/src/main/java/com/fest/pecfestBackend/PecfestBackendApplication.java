package com.fest.pecfestBackend;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fest.pecfestBackend.entity.User;
import com.fest.pecfestBackend.repository.UserRepo;

@SpringBootApplication
@ComponentScan({"com.fest.pecfestBackend"})
public class PecfestBackendApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PecfestBackendApplication.class, args);
	}
	
}
