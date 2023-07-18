package com.springstudie.supermarket.controller;

import com.springstudie.supermarket.entity.Product;
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
    private final ProductServices productServices;

    ProductsAPIController(ProductRepository productRepository, ProductServices productServices){
        this.productRepository = productRepository;
        this.productServices = productServices;
    }

    /*
     * Method to post a product in API
     * The first method apply for directly requests from API.
     * The second method apply for the requests that comes from the /products/register form.
     *
     * @Author: github.com/r1beirin
     * @Year: 2023
     *
     * Status code:
     *  200 - Successful: it's ok in your request
     *  400 - Bad request: irregular parameter in any field
     *  422 - Unprocessable Entity: error in any fields.
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
        if(productServices.isValidJson(productFromJson.toString())) {
            if(productServices.isValidField(productFromJson)) {
                Product product = new Product();
                productServices.setProductField(product, productFromJson);
                productRepository.save(product);

                return productServices.onSuccessMessage();
            } else return productServices.onIllegalArgumentMessage();
        }
        else return productServices.onIllegalArgumentMessage();
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
    public ResponseEntity<Product> getProduct(@PathVariable long id){
        Product product = productRepository.findById(id);

        if(productServices.isProductNotExists(product)) return productServices.onNotFoundMessage();
        else return productServices.onSuccessMessage(product);
    }

    @GetMapping("/")
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    /*
     * Method to update a product in API
     * The first method apply for directly requests from API.
     * The second method apply for the requests that comes from the /products/register form.
     *
     * @Author: github.com/r1beirin
     * @Year: 2023
     *
     * Status code:
     *  200 - Successful: it's ok in your request
     *  400 - Bad request: irregular parameter in any field
     *  404 - Not found: resource not exist
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
        if(productServices.isValidJson(productFromJson.toString())) {
            if(productServices.isValidField(productFromJson)) {
                Product oldProduct = productRepository.findById(id);

                if(productServices.isProductNotExists(oldProduct)) return productServices.onNotFoundMessage();

                productServices.setProductField(oldProduct, productFromJson);
                productRepository.saveAndFlush(oldProduct);

                return productServices.onSuccessMessage();
            } else return productServices.onIllegalArgumentMessage();
        }
        else return productServices.onIllegalArgumentMessage();
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

        if(productServices.isProductNotExists(product)) return productServices.onNotFoundMessage();
        else{
            productRepository.deleteById(id);
            return productServices.onSuccessMessage();
        }
    }
}
