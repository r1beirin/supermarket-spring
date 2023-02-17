/*
 *
 * @Author: github.com/r1beirin
 * @Year: 2023
 *
 */

package com.springstudie.supermarket.services;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springstudie.supermarket.model.usecases.Product;
import org.json.simple.JSONObject;
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
    public static void setProductField(Product product, JSONObject productUpdated){
        String nameProduct = (String) productUpdated.get("nameProduct");
        var valueProduct = Double.parseDouble(String.valueOf(productUpdated.get("valueProduct")));
        String descriptionProduct = (String) productUpdated.get("descriptionProduct");
        var expirationProductAt = (String) productUpdated.get("expirationProductAt");

        product.setNameProduct(nameProduct != null ? nameProduct : product.getNameProduct());
        product.setValueProduct(productUpdated.get("valueProduct") != null ? valueProduct : product.getValueProduct());
        product.setDescriptionProduct(descriptionProduct != null ? descriptionProduct : product.getDescriptionProduct());
        product.setExpirationProductAt(expirationProductAt != null ? LocalDate.parse(expirationProductAt) : product.getExpirationProductAt());
    }
}
