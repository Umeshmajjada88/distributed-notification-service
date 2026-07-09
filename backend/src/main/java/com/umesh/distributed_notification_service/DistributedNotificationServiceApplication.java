package com.umesh.distributed_notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DistributedNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(
				DistributedNotificationServiceApplication.class,
				args);
	}

}