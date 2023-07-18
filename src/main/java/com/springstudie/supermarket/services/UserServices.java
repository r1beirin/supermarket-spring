package com.springstudie.supermarket.services;

import com.springstudie.supermarket.model.User;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class UserServices {

    /**
     * This method validate a field in request.
     * Regex: [a-zA-Z\\s]+ -> to validate just word.
     * Regex: ^ *$ -> to validate wordspaces.
     * @param user
     * @return
     */
    public static boolean isValidField(User user){
        return  user.getName() != null &&
                user.getName().matches("[a-zA-Z\\s]+") &&
                !user.getName().matches("^ *$") &&
                user.getEmail() != null &&
                user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") &&
                !user.getEmail().matches("^ *$") &&
                user.getPassword() != null;
    }

    /**
     * This method encrypt a password with SHA-256 and encode to hexadecimal.
     * @param user
     */
    public static void encryptPassword(User user){
        String password = user.getPassword();
        StringBuilder hexStringPass = new StringBuilder();

        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = algorithm.digest(password.getBytes(StandardCharsets.UTF_8));

            for (byte b : messageDigest) {
                hexStringPass.append(String.format("%02X", 0xFF & b));
            }
            String hexPass = hexStringPass.toString();
            user.setPassword(hexPass);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is used to compare two passwords. One is input from user, second is output of existing user.
     * @param userFromLogin
     * @param userFromQuery
     * @return
     */
    public static boolean comparePassword(User userFromLogin, User userFromQuery){
        encryptPassword(userFromLogin);

        return userFromLogin.getPassword().equals(userFromQuery.getPassword());
    }
}
