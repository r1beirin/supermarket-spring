package com.springstudie.supermarket.controller;

import com.springstudie.supermarket.entity.User;
import com.springstudie.supermarket.repository.UserRepository;
import com.springstudie.supermarket.services.UserServices;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/user")
public class UserControllerAPI {
    private final UserServices userServices;

    public UserControllerAPI(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping(value = "/create/")
    public JSONObject registerUser(User user){
        JSONObject jsonObject = new JSONObject();

        userServices.register(user, jsonObject);

        return jsonObject;
    }

    @PostMapping(value = "/login/")
    public JSONObject loginUser(String email, String password){
        JSONObject jsonObject = new JSONObject();

        userServices.login(email, password, jsonObject);

        return jsonObject;
    }
}
