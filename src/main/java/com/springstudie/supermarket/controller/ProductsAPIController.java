package com.springstudie.supermarket.controller;

import com.springstudie.supermarket.model.usecases.Product;
import com.springstudie.supermarket.repository.ProductRepository;
import com.springstudie.supermarket.services.ProductServices;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping( "/api/products")
public class ProductsAPIController {

    private final ProductRepository productRepository;
    ProductsAPIController(ProductRepository productRepository){
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
        if(ProductServices.isValidJson(productToBeConverted.toString())) {
            if(ProductServices.isValidField(productToBeConverted)) {
                Product product = new Product();
                ProductServices.setProductField(product, productToBeConverted);
                productRepository.save(product);

                return ProductServices.onSuccessMessage();
            } else return ProductServices.onIllegalArgumentMessage();
        }
        else return ProductServices.onIllegalArgumentMessage();
    }

    /*
     * Method to get a product in API
     *
     * @Author: github.com/r1beirin
     * @Year: 2023
     *
     * Status code:
     *  200 - Successful: it's ok in your request
     *  404 - Not found: resource not exist
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Product> getProduct(@PathVariable long id){
        Product product = productRepository.findById(id);

        if(ProductServices.isProductExists(product)) return ProductServices.onNotFoundMessage();
        else return ProductServices.onSuccessMessage(product);
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
     */
    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody JSONObject productUpdated) {

        if(ProductServices.isValidJson(productUpdated.toString())) {
            if(ProductServices.isValidField(productUpdated)) {
                Product oldProduct = productRepository.findById(id);

                if(ProductServices.isProductExists(oldProduct)) return ProductServices.onNotFoundMessage();

                ProductServices.setProductField(oldProduct, productUpdated);
                productRepository.saveAndFlush(oldProduct);

                return ProductServices.onSuccessMessage();
            } else return ProductServices.onIllegalArgumentMessage();
        }
        else return ProductServices.onIllegalArgumentMessage();
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
        Product product = productRepository.findById(id);

        if(ProductServices.isProductExists(product)) return ProductServices.onNotFoundMessage();
        else{
            productRepository.deleteById(id);
            return ProductServices.onSuccessMessage();
        }
    }
}
