package com.springstudie.supermarket.controller;

import com.springstudie.supermarket.repository.ProductRepository;
import com.springstudie.supermarket.services.ProductServices;
import com.springstudie.supermarket.model.usecases.Product;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/api/products")
public class ProductController {

    private final ProductRepository productRepository;
    ProductController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @PostMapping( "/")
    @ResponseBody
    public ResponseEntity<Product> postProduct(@RequestBody JSONObject productToBeConverted){
        try{
            // Se o request ta valido continua
            if(ProductServices.isJSONValid(productToBeConverted.toString())){
                Product product = new Product();
                ProductServices.setProductField(product, productToBeConverted);
                productRepository.save(product);

                return ResponseEntity.ok().build();
            }
            //  Request não ta valido
            else return ResponseEntity
                    .notFound()
                    .build();
        }
        // algum campo vazio
        catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(null);
        }
        //return productRepository.save(product);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Product> getProduct(@PathVariable long id){
        try{
            return ResponseEntity
                    .ok()
                    .body(productRepository.findById(id));
        }
        catch (Exception e) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/")
    @ResponseBody
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    /*
     * Method to update a product in API
     *
     * @Author: github.com/r1beirin
     * @Year: 2023
     *
     * Status code:
     *  200 - Successful: it's ok in your request
     *  400 - Bad request: irregular parameter in any field
     *  404 - Not found: resource not exist
     *  422 - Unprocessable Entity: error in any fields.
     */
    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Product> putProduct(@PathVariable long id, @RequestBody JSONObject productUpdated) {
        try {
            if(ProductServices.isJSONValid(productUpdated.toString())) {
                Product oldProduct = productRepository.findById(id);

                if(oldProduct == null) return ResponseEntity
                                                .notFound()
                                                .build();
                else {
                    ProductServices.setProductField(oldProduct, productUpdated);

                    Product newProduct = productRepository.saveAndFlush(oldProduct);
                    return ResponseEntity
                            .ok()
                            .body(newProduct);
                }
            }
            else return ResponseEntity
                            .badRequest()
                            .build();
        }
        catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(null);
        }
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable long id){
        productRepository.deleteById(id);
        return "Ok";
    }

    @DeleteMapping("/")
    public void deleteAllProducts(){
        productRepository.deleteAll();
    }
}
