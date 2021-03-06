package com.rest.auction.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rest.auction.models.Product;


//No need implementation, just one interface, and you have CRUD, thanks Spring Data!
@Repository
public interface ProductRepository extends MongoRepository<Product, String>  {


}
