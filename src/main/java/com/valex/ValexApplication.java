package com.valex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication (exclude = { SecurityAutoConfiguration.class })
@EntityScan (basePackages = "com.valex.demon.model")
public class ValexApplication {

	public static void main(String[] args) {

		SpringApplication.run(ValexApplication.class, args);

		}
		@Bean
		public PasswordEncoder getEncoder () {

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			return encoder;
		}

}
