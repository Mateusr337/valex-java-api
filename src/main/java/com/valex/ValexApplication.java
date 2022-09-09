package com.valex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan (basePackages = "com.valex.demon.model")
public class ValexApplication {

	public static void main(String[] args) {

		SpringApplication.run(ValexApplication.class, args);

	}

}
