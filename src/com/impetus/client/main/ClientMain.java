package com.impetus.client.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = { "com.impetus.client.controller" })
public class ClientMain {

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "measurement-client-server");
		SpringApplication.run(ClientMain.class, args);
	}
}
