package com.springstudie.supermarket.controller;

import com.springstudie.supermarket.model.usecases.User;
import com.springstudie.supermarket.repository.ProductRepository;
import com.springstudie.supermarket.repository.UserRepository;
import com.springstudie.supermarket.services.UserServices;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/api/user")
public class UserControllerAPI {

    private final UserRepository userRepository;

    public UserControllerAPI(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping( value = "/", consumes = "application/json")
    public JSONObject registerUser(@RequestBody JSONObject userFromJson){
        JSONObject obj = new JSONObject();

        if(UserServices.isValidField(userFromJson)){
            User user = new User();
            UserServices.setProductField(user, userFromJson);
            userRepository.save(user);

            obj.put("valid", true);
            return obj;
        }

        obj.put("valid", false);
        return obj;
    }
}
