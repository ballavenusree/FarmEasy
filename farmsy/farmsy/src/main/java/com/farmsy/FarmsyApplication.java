package com.farmsy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FarmsyApplication {
	public static void main(String[] args) {
		SpringApplication.run(FarmsyApplication.class, args);
	}
}