package com.springstudie.supermarket.util;

import com.springstudie.supermarket.entity.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {
    public static void encrypt(User user){
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
    }

    public static boolean compare(String password, String hash){
        return BCrypt.checkpw(password, hash);
    }
}
