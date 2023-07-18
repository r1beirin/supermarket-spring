package com.springstudie.supermarket.services;

import com.springstudie.supermarket.model.Product;
import com.springstudie.supermarket.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserServices {
    boolean isValidField(User user);
    void encryptPassword(User user);
    String encryptPassword(String password);
    boolean comparePassword(String password, User userFromQuery);
}
