package com.springstudie.supermarket.services;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springstudie.supermarket.model.usecases.Product;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.time.LocalDate;

public class ProductServices {

    public static boolean isProductExists(Product product){
        return product == null;
    }

    /*
     * This a method to json request validator.
     *
     * @Author: github.com/r1beirin
     * @Year: 2023
     */
    public static boolean isValidJson(String json) {
        ObjectMapper mapper = new ObjectMapper()
                .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);

        try{
            mapper.readTree(json);
        }catch (JacksonException e){
            return false;
        }
        return true;
    }

    /*
     * This method validate a field in json request.
     * Regex: [a-zA-Z\\s]+ -> to validate just word.
     * Regex: ^ *$ -> to validate wordspaces.
     *
     * @Author: github.com/r1beirin
     * @Year: 2023
     */
    public static boolean isValidField(JSONObject product){
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

    /*
     * This method set a field in json request.
     *
     * @Author: github.com/r1beirin
     * @Year: 2023
     */
    public static void setProductField(Product product, JSONObject productToBeConverted){
        String nameProduct = (String) productToBeConverted.get("nameProduct");
        var valueProduct = Double.parseDouble(String.valueOf(productToBeConverted.get("valueProduct")));
        String descriptionProduct = (String) productToBeConverted.get("descriptionProduct");
        var expirationProductAt = (String) productToBeConverted.get("expirationProductAt");

        product.setNameProduct(nameProduct);
        product.setValueProduct(valueProduct);
        product.setDescriptionProduct(descriptionProduct);
        product.setExpirationProductAt(LocalDate.parse(expirationProductAt));
    }

    /*
     * Below are methods to flag the status code on requests.
     *
     * @Author: github.com/r1beirin
     * @Year: 2023
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Some parameters are invalid")
    public static ResponseEntity<Product> onIllegalArgumentMessage() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not exist")
    public static ResponseEntity<Product> onNotFoundMessage(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Successful")
    public static ResponseEntity<Product> onSuccessMessage(){
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Successful")
    public static ResponseEntity<Product> onSuccessMessage(Product product){
        return ResponseEntity.ok(product);
    }
}
