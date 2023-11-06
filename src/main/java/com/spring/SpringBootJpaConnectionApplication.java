package com.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.mssql.models.Speaker;

@SpringBootApplication
public class SpringBootJpaConnectionApplication {

	private static final Logger logger = LogManager.getLogger(Speaker.class.getName());
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaConnectionApplication.class, args);
		
		// An example for the use of the logger variable of log4j2.
		logger.debug("Project started!!!");
	}
}
