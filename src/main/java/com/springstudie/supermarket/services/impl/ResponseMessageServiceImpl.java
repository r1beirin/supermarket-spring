package com.springstudie.supermarket.services.impl;

import com.springstudie.supermarket.entity.Product;
import com.springstudie.supermarket.services.ResponseMessagesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResponseMessageServiceImpl implements ResponseMessagesService {
    @Override
    @ResponseStatus(value = HttpStatus.OK, reason = "Successful")
    public ResponseEntity<Product> onSuccessMessage(Product product) {
        return ResponseEntity.ok(product);
    }

    @Override
    @ResponseStatus(value = HttpStatus.OK, reason = "Successful")
    public ResponseEntity<Product> onSuccessMessage() {
        return ResponseEntity.ok().build();
    }

    @Override
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not exist")
    public ResponseEntity<Product> onNotFoundMessage() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Some parameters are invalid")
    public ResponseEntity<Product> onIllegalArgumentMessage() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
