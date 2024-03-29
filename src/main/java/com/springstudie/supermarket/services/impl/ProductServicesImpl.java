package com.springstudie.supermarket.services.impl;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springstudie.supermarket.entity.Product;
import com.springstudie.supermarket.repository.ProductRepository;
import com.springstudie.supermarket.services.ProductServices;
import com.springstudie.supermarket.services.ResponseMessagesService;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductServicesImpl implements ProductServices {
    private final ProductRepository productRepository;
    private final ResponseMessagesService responseMessagesService;

    public ProductServicesImpl(ProductRepository productRepository, ResponseMessagesService responseMessagesService) {
        this.productRepository = productRepository;
        this.responseMessagesService = responseMessagesService;
    }

    @Override
    public boolean isProductNotExists(Product product){
        return product == null;
    }

    @Override
    public boolean isValidJson(String json) {
        ObjectMapper mapper = new ObjectMapper()
                .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);

        try{
            mapper.readTree(json);
            return true;
        }
        catch (JacksonException e){
            return false;
        }
    }

    @Override
    public void product2json(Product product, JSONObject json){
        json.put("nameProduct", product.getNameProduct());
        json.put("valueProduct", product.getValueProduct());
        json.put("descriptionProduct", product.getDescriptionProduct());
        json.put("expirationProductAt", String.valueOf(product.getExpirationProductAt()));
    }

    /**
     * This method validate a field in json request.
     * Regex: [a-zA-Z\\s]+ -> to validate just word.
     * Regex: ^ *$ -> to validate wordspaces.
     */
    @Override
    public boolean isValidField(JSONObject product){
        return  product.get("nameProduct") != null &&
                product.get("nameProduct").toString().matches("[a-zA-Z\\s]+") &&
                !product.get("nameProduct").toString().matches("^ *$") &&
                product.get("valueProduct") != null &&
                !(Double.parseDouble(String.valueOf(product.get("valueProduct"))) < 0) &&
                product.get("descriptionProduct") != null &&
                !product.get("descriptionProduct").toString().matches("^ *$") &&
                product.get("expirationProductAt") != null &&
                !product.get("expirationProductAt").toString().matches("^ *$");
    }

    @Override
    public void setProductField(Product product, JSONObject productToBeConverted){
        String nameProduct = (String) productToBeConverted.get("nameProduct");
        var valueProduct = Double.parseDouble(String.valueOf(productToBeConverted.get("valueProduct")));
        String descriptionProduct = (String) productToBeConverted.get("descriptionProduct");
        var expirationProductAt = (String) productToBeConverted.get("expirationProductAt");

        product.setNameProduct(nameProduct);
        product.setValueProduct(valueProduct);
        product.setDescriptionProduct(descriptionProduct);
        product.setExpirationProductAt(LocalDate.parse(expirationProductAt));
    }

    @Override
    public void postProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.saveAndFlush(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public ResponseEntity<Product> delete(long id) {

        Product product = productRepository.findById(id);

        if(this.isProductNotExists(product))
            return responseMessagesService.onNotFoundMessage();
        else{
            productRepository.deleteById(id);
            return responseMessagesService.onSuccessMessage();
        }
    }

    @Override
    public ResponseEntity<Product> getUpdateProductResponseEntity(JSONObject productFromJson, long id) {
        if(this.isValidJson(productFromJson.toString())) {
            if(this.isValidField(productFromJson)) {
                Product oldProduct = productRepository.findById(id);

                if(this.isProductNotExists(oldProduct))
                    return responseMessagesService.onNotFoundMessage();

                this.setProductField(oldProduct, productFromJson);
                productRepository.saveAndFlush(oldProduct);

                return responseMessagesService.onSuccessMessage();
            }
            else
                return responseMessagesService.onIllegalArgumentMessage();
        }
        else
            return responseMessagesService.onIllegalArgumentMessage();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ResponseEntity<Product> getProduct(long id) {
        Product product = productRepository.findById(id);

        if(this.isProductNotExists(product))
            return responseMessagesService.onNotFoundMessage();
        else
            return responseMessagesService.onSuccessMessage(product);
    }

    @Override
    public ResponseEntity<Product> getPostProductResponseEntity(JSONObject productFromJson) {
        if(this.isValidJson(productFromJson.toString())) {
            if(this.isValidField(productFromJson)) {
                Product product = new Product();
                this.setProductField(product, productFromJson);
                productRepository.save(product);

                return responseMessagesService.onSuccessMessage();
            }
            else
                return responseMessagesService.onIllegalArgumentMessage();
        }
        else
            return responseMessagesService.onIllegalArgumentMessage();
    }
}
