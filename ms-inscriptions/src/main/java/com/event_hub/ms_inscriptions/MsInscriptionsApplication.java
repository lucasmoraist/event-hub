package com.event_hub.ms_inscriptions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class MsInscriptionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsInscriptionsApplication.class, args);
	}

}
