package com.springstudie.supermarket.services.impl;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springstudie.supermarket.entity.Product;
import com.springstudie.supermarket.services.ProductServices;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.time.LocalDate;

@Service
public class ProductServicesImpl implements ProductServices {

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
        }catch (JacksonException e){
            return false;
        }
        return true;
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
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Some parameters are invalid")
    public ResponseEntity<Product> onIllegalArgumentMessage() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    @Override
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not exist")
    public ResponseEntity<Product> onNotFoundMessage(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    @ResponseStatus(value = HttpStatus.OK, reason = "Successful")
    public ResponseEntity<Product> onSuccessMessage(){
        return ResponseEntity.ok().build();
    }

    @Override
    @ResponseStatus(value = HttpStatus.OK, reason = "Successful")
    public ResponseEntity<Product> onSuccessMessage(Product product){
        return ResponseEntity.ok(product);
    }
}
