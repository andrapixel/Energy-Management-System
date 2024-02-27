package com.example.monitoringmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:device-config.properties")
public class MonitoringMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringMicroserviceApplication.class, args);
	}

}
