package com.pmb.paymybuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {JdbcTemplateAutoConfiguration.class})
@EnableJpaRepositories(basePackages = {"com.pmb.paymybuddy.repository"})
public class P6PayMyBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(P6PayMyBuddyApplication.class, args);
	}

}
