package com.springstudie.supermarket.services;

import com.springstudie.supermarket.entity.Product;
import org.springframework.http.ResponseEntity;

public interface ResponseMessagesService {
    ResponseEntity<Product> onSuccessMessage(Product product);
    ResponseEntity<Product> onSuccessMessage();
    ResponseEntity<Product> onNotFoundMessage();
    ResponseEntity<Product> onIllegalArgumentMessage();
}
