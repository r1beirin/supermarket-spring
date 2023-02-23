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
        ProductServices.product2json(product, productFromJson);

        return getPostProductResponseEntity(productFromJson);
    }
    public ResponseEntity<Product> getPostProductResponseEntity(@RequestBody JSONObject productFromJson) {
        if(ProductServices.isValidJson(productFromJson.toString())) {
            if(ProductServices.isValidField(productFromJson)) {
                Product product = new Product();
                ProductServices.setProductField(product, productFromJson);
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
    public ResponseEntity<Product> getProduct(@PathVariable long id){
        Product product = productRepository.findById(id);

        if(ProductServices.isProductNotExists(product)) return ProductServices.onNotFoundMessage();
        else return ProductServices.onSuccessMessage(product);
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
        ProductServices.product2json(product, productFromJson);

        return getUpdateProductResponseEntity(productFromJson, id);
    }
    public ResponseEntity<Product> getUpdateProductResponseEntity(JSONObject productFromJson, long id) {
        if(ProductServices.isValidJson(productFromJson.toString())) {
            if(ProductServices.isValidField(productFromJson)) {
                Product oldProduct = productRepository.findById(id);

                if(ProductServices.isProductNotExists(oldProduct)) return ProductServices.onNotFoundMessage();

                ProductServices.setProductField(oldProduct, productFromJson);
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

        if(ProductServices.isProductNotExists(product)) return ProductServices.onNotFoundMessage();
        else{
            productRepository.deleteById(id);
            return ProductServices.onSuccessMessage();
        }
    }
}
