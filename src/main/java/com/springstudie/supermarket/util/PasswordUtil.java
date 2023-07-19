package com.springstudie.supermarket.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {
    public static String encrypt(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
