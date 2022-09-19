package com.valex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EntityScan (basePackages = "com.valex.domain.model")
public class ValexApplication {
	public static void main (String[] args) {

		SpringApplication.run(ValexApplication.class, args);

		}
		@Bean
		public PasswordEncoder getEncoder () {
			return new BCryptPasswordEncoder();
		}

}
