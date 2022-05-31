package com.rest.auction.controllers;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.auction.exception.ResourceNotFoundException;
import com.rest.auction.models.Bid;
import com.rest.auction.models.Product;
import com.rest.auction.services.AuctionBuyerService;

@RestController
@RequestMapping("/e-auction/api/v1")
public class AuctionBuyerController {
	
	@Autowired
	private AuctionBuyerService auctionService;

	public static final Logger log = LoggerFactory.getLogger(AuctionBuyerController.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(value= "/buyer/place-bid")
	public ResponseEntity<?> placeBid(@RequestBody Bid bid) {
		try {
			Optional<Product> product = auctionService.getProduct(bid.getProductId());
			if (!product.isPresent()) {
                throw new ResourceNotFoundException("Cant find any product with the given ID-"
                		+ "ProductId must be of an existing product available for auction ");
            }
			Optional<Bid> addBid = auctionService.getBidByProduct(bid.getProductId(),bid.getEmail());
			if (addBid.isPresent()) {
                throw new RuntimeException("More than one bid on a product by same user is not allowed  ");
            }
			System.out.println("Current date:"+new Date());
			if(new Date().after(product.get().getBidEndDate())) {
				throw new RuntimeException("Bid cannot be placed after product bid end date");
			}
			Bid  addedBid= auctionService.saveBid(bid);
			log.info("Bid added: {}", addedBid);
			return new ResponseEntity(addedBid, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Bid not added:", e);
			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PutMapping(value = "/buyer/update-bid/{productId}/{buyerEmailld}/{newBidAmount}")
	public ResponseEntity<?> updateBid(@PathVariable(value="productId") String productId, @PathVariable(value="buyerEmailld") String buyerEmailld, 
			@PathVariable(value="newBidAmount") double newBidAmount) {
		try {
			Optional<Product> product = auctionService.getProduct(productId);
			if (!product.isPresent()) {
                throw new ResourceNotFoundException("Cant find any product with the given ID-"
                		+ "ProductId must be of an existing product available for auction ");
            }
			System.out.println("Current date:"+new Date());
			if(new Date().after(product.get().getBidEndDate())) {
				throw new RuntimeException("Bid cannot be updated after product bid end date");
			}
			Bid  updatedBid= auctionService.updateBid(productId,buyerEmailld,newBidAmount);
			
			log.info("Bid details updated: {}", updatedBid);
			return new ResponseEntity(updatedBid, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Bid details not updated:", e);
			return new ResponseEntity("Bid details not updated", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
