package com.springstudie.supermarket.services;

import com.springstudie.supermarket.entity.Product;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public interface ProductServices {
    boolean isProductNotExists(Product product);
    boolean isValidJson(String json);
    void product2json(Product product, JSONObject json);
    boolean isValidField(JSONObject product);
    void setProductField(Product product, JSONObject productToBeConverted);
    ResponseEntity<Product> delete(long id);
    ResponseEntity<Product> getUpdateProductResponseEntity(JSONObject productFromJson, long id);
    List<Product> getAllProducts();
    ResponseEntity<Product> getProduct(@PathVariable long id);
    ResponseEntity<Product> getPostProductResponseEntity(@RequestBody JSONObject productFromJson);
}
