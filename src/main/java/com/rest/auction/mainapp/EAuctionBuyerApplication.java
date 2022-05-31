package com.rest.auction.mainapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan({"com.rest.auction.controllers,com.rest.auction.services,com.rest.auction.models,com.rest.auction.mainapp"
})
@EnableMongoRepositories("com.rest.auction.repository")
public class EAuctionBuyerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EAuctionBuyerApplication.class, args);
	}

}
