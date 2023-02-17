package com.springstudie.supermarket.controller;

import com.springstudie.supermarket.services.ProductServices;
import com.springstudie.supermarket.repository.ProductRepository;
import com.springstudie.supermarket.model.usecases.Product;
import org.json.simple.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
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


    /*
     * Method to post a product in API
     *
     * @Author: github.com/r1beirin
     * @Year: 2023
     *
     * Status code:
     *  200 - Successful: it's ok in your request
     *  400 - Bad request: irregular parameter in any field
     *  422 - Unprocessable Entity: error in any fields.
     */
    @PostMapping( "/")
    @ResponseBody
    public ResponseEntity<Product> postProduct(@RequestBody JSONObject productToBeConverted){
        try{
            if(ProductServices.isJSONValid(productToBeConverted.toString())){
                Product product = new Product();
                try {
                    ProductServices.setProductField(product, productToBeConverted);
                    productRepository.save(product);

                    return ProductServices.onSuccessRequest();
                }
                catch (IllegalArgumentException e){
                    return ProductServices.onIllegalArgumentException();
                }
            }

            else return ProductServices.onIllegalArgumentException();
        }
        catch (IllegalArgumentException e){
            return ProductServices.onIllegalFieldException();
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Product getProduct(@PathVariable long id){
        return productRepository.findById(id);
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

                if(oldProduct == null) return ProductServices.onNotFoundException();
                else {
                    try {
                        ProductServices.setProductField(oldProduct, productUpdated);

                        productRepository.saveAndFlush(oldProduct);
                        return ProductServices.onSuccessRequest();
                    }
                    catch (IllegalArgumentException e){
                        return ProductServices.onIllegalArgumentException();
                    }
                }
            }
            else return ProductServices.onIllegalArgumentException();
        }
        catch (Exception e){
            return ProductServices.onIllegalFieldException();
        }
    }



    /*
     * Method to delete a product in API
     *
     * @Author: github.com/r1beirin
     * @Year: 2023
     *
     * Status code:
     *  200 - Successful: it's ok in your request
     *  404 - Not found: resource not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable long id){
        try{
            productRepository.deleteById(id);
            return ProductServices.onSuccessRequest();
        }
        catch (EmptyResultDataAccessException e){
            return ProductServices.onNotFoundException();
        }
    }

    @DeleteMapping("/")
    public void deleteAllProducts(){
        productRepository.deleteAll();
    }
}
