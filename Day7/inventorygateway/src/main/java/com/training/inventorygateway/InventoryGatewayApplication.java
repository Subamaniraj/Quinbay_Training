package com.training.inventorygateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.*;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryGatewayApplication.class, args);
	}
}