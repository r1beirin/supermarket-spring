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

    /*
     * This a method to json request validator.
     * @Author: github.com/r1beirin
     * @Year: 2023
     *
     */
    public static boolean isJSONValid(String jsonToString) {
        ObjectMapper mapper = new ObjectMapper()
                .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS);

        try{
            mapper.readTree(jsonToString);
        }catch (JacksonException e){
            return false;
        }
        return true;
    }

    /*
     * This method set a field in json request.
     *
     * @Author: github.com/r1beirin
     * @Year: 2023
     *
     */
public static void setProductField(Product product, JSONObject productUpdated) throws IllegalArgumentException{
    if(productUpdated.get("nameProduct") == null || productUpdated.get("valueProduct") == null || productUpdated.get("descriptionProduct") == null || productUpdated.get("expirationProductAt") == null){
        throw new IllegalArgumentException();
    }

    String nameProduct = (String) productUpdated.get("nameProduct");
    var valueProduct = Double.parseDouble(String.valueOf(productUpdated.get("valueProduct")));
    String descriptionProduct = (String) productUpdated.get("descriptionProduct");
    var expirationProductAt = (String) productUpdated.get("expirationProductAt");

    product.setNameProduct(nameProduct != null ? nameProduct : product.getNameProduct());
    product.setValueProduct(productUpdated.get("valueProduct") != null ? valueProduct : product.getValueProduct());
    product.setDescriptionProduct(descriptionProduct != null ? descriptionProduct : product.getDescriptionProduct());
    product.setExpirationProductAt(expirationProductAt != null ? LocalDate.parse(expirationProductAt) : product.getExpirationProductAt());
}

    /*
     * Below are methods to flag the status code on requests.
     *
     * @Author: github.com/r1beirin
     * @Year: 2023
     *
     */
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Some fields are invalid")
    public static ResponseEntity<Product> onIllegalFieldException(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
    }
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Some parameters are invalid")
    public static ResponseEntity<Product> onIllegalArgumentException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Successful")
    public static ResponseEntity<Product> onSuccessRequest(){
        return ResponseEntity.ok().build();
    }
}
