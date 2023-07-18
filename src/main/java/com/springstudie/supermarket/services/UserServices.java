package com.springstudie.supermarket.services;

import com.springstudie.supermarket.model.usecases.Product;
import com.springstudie.supermarket.model.usecases.User;
import org.json.simple.JSONObject;

import java.time.LocalDate;

public class UserServices {

    /*
     * This method validate a field in json request.
     * Regex: [a-zA-Z\\s]+ -> to validate just word.
     * Regex: ^ *$ -> to validate wordspaces.
     *
     * @Author: github.com/r1beirin
     * @Year: 2023
     */
    public static boolean isValidField(JSONObject user){
        return  user.get("nome") != null &&
                user.get("nome").toString().matches("[a-zA-Z\\s]+") &&
                !user.get("nome").toString().matches("^ *$") &&
                user.get("email") != null &&
                user.get("email").toString().matches("/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$/") &&
                !user.get("email").toString().matches("^ *$") &&
                user.get("password") != null;
    }

    /*
     * This method set a field in json request.
     *
     * @Author: github.com/r1beirin
     * @Year: 2023
     */
    public static void setProductField(User user, JSONObject productToBeConverted){
        String nome = (String) productToBeConverted.get("nome");
        String email = (String) productToBeConverted.get("email");
        String password = (String) productToBeConverted.get("password");

        user.setName(nome);
        user.setEmail(email);
        user.setPassword(password);
    }
}
