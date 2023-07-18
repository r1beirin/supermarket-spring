package com.springstudie.supermarket.controller;

import com.springstudie.supermarket.model.User;
import com.springstudie.supermarket.repository.UserRepository;
import com.springstudie.supermarket.services.UserServices;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api/user")
public class UserControllerAPI {

    private final UserRepository userRepository;

    public UserControllerAPI(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/create/")
    public JSONObject registerUser(User user){
        JSONObject jsonObject = new JSONObject();
        if(UserServices.isValidField(user)){
            UserServices.encryptPassword(user);
            userRepository.save(user);
            jsonObject.put("valid", true);
            return jsonObject;
        }

        jsonObject.put("valid", false);
        return jsonObject;
    }

    @PostMapping(value = "/login/")
    public JSONObject loginUser(String email, String password){
        JSONObject jsonObject = new JSONObject();
        User user = userRepository.findByEmail(email);

        if(UserServices.comparePassword(password, user)){
            jsonObject.put("valid", true);
            return jsonObject;
        }

        jsonObject.put("valid", false);
        return jsonObject;
    }
}
