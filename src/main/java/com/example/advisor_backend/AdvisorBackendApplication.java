package com.example.advisor_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.advisor_backend.mapper")
public class AdvisorBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(AdvisorBackendApplication.class, args);
	}
}

