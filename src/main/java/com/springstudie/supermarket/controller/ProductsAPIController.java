package com.springstudie.supermarket.controller;

import com.springstudie.supermarket.entity.Product;
import com.springstudie.supermarket.services.ProductServices;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping( "/api/products")
public class ProductsAPIController {
    private final ProductServices productServices;

    ProductsAPIController(ProductServices productServices){
        this.productServices = productServices;
    }

    /*
     * Method to post a product in API
     * The first method apply for directly requests from API.
     * The second method apply for the requests that comes from the /products/register form.
     *
     */
    @PostMapping( value = "/", consumes = "application/json")
    public ResponseEntity<Product> postProduct(@RequestBody JSONObject product){
        return getPostProductResponseEntity(product);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Product> postProduct(Product product){
        JSONObject productFromJson = new JSONObject();
        productServices.product2json(product, productFromJson);

        return getPostProductResponseEntity(productFromJson);
    }
    public ResponseEntity<Product> getPostProductResponseEntity(@RequestBody JSONObject productFromJson) {
        return productServices.getPostProductResponseEntity(productFromJson);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id){
        return productServices.getProduct(id);
    }

    @GetMapping("/")
    public List<Product> getAllProducts(){
        return productServices.getAllProducts();
    }

    /*
     * Method to update a product in API
     * The first method apply for directly requests from API.
     * The second method apply for the requests that comes from the /products/register form.
     */
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, JSONObject productUpdated) {
        return getUpdateProductResponseEntity(productUpdated, id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, Product product){
        JSONObject productFromJson = new JSONObject();
        productServices.product2json(product, productFromJson);

        return getUpdateProductResponseEntity(productFromJson, id);
    }
    public ResponseEntity<Product> getUpdateProductResponseEntity(JSONObject productFromJson, long id) {
        return productServices.getUpdateProductResponseEntity(productFromJson, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable long id){
        return productServices.delete(id);
    }
}
