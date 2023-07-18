package com.springstudie.supermarket.services;

import com.springstudie.supermarket.model.Product;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ProductServices {
    boolean isProductNotExists(Product product);
    boolean isValidJson(String json);
    void product2json(Product product, JSONObject json);
    boolean isValidField(JSONObject product);
    void setProductField(Product product, JSONObject productToBeConverted);
    ResponseEntity<Product> onIllegalArgumentMessage();
    ResponseEntity<Product> onNotFoundMessage();
    ResponseEntity<Product> onSuccessMessage();
    ResponseEntity<Product> onSuccessMessage(Product product);
}
