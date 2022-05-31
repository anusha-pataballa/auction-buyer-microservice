package com.rest.auction.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.auction.controllers.AuctionBuyerController;
import com.rest.auction.models.Bid;
import com.rest.auction.models.Product;
import com.rest.auction.repository.BidRepository;
import com.rest.auction.repository.ProductRepository;

@Service
public class AuctionBuyerService {

	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
    private BidRepository bidRepository;
	
	public static final Logger log = LoggerFactory.getLogger(AuctionBuyerController.class);

	public Bid saveBid(Bid bid) {
		return bidRepository.save(bid);
	}
	
	public Bid updateBid(String productId,String emailId, double bidAmount) {
		Optional<Bid> bid=bidRepository.findByProductIdAndEmail(productId, emailId);
		Bid updatedBid = bid.get();
		updatedBid.setBidAmount(bidAmount);
		return bidRepository.save(updatedBid);
	}
	
	public List<Bid> getAllBids(String productId){
		return bidRepository.findAllByProductId(productId);
	}
	
	public Optional<Product> getProduct(String productId) {
		return productRepository.findById(productId);
	}
	
	public Optional<Bid> getBidByProduct(String productId, String email){
		return bidRepository.findByProductIdAndEmail(productId, email);
	}
	
	

}
