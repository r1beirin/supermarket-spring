package com.springstudie.supermarket.services;

import com.springstudie.supermarket.entity.User;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface UserServices {
    boolean isValidField(User user);
    void register(User user, JSONObject jsonObject);
    boolean existByEmail(String email);
    void login(String email, String password, JSONObject jsonObject);
}
