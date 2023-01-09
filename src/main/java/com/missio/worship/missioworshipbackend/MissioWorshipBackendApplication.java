package com.missio.worship.missioworshipbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class MissioWorshipBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MissioWorshipBackendApplication.class, args);
	}

}
