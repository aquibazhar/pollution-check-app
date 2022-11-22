package com.pollution.watchlistservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WatchlistServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(WatchlistServiceApplication.class, args);
	}

}
