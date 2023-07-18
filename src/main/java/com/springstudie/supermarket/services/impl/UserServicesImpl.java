package com.springstudie.supermarket.services.impl;

import com.springstudie.supermarket.entity.User;
import com.springstudie.supermarket.repository.UserRepository;
import com.springstudie.supermarket.services.UserServices;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class UserServicesImpl implements UserServices {
    private final UserRepository userRepository;

    public UserServicesImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method validate a field in request.
     * Regex: [a-zA-Z\\s]+ -> to validate just word.
     * Regex: ^ *$ -> to validate wordspaces.
     * @param user User object
     * @return boolean
     */
    @Override
    public boolean isValidField(User user){
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
     * @param user User object
     */
    @Override
    public void encryptPassword(User user){
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
     * This method encrypt a password with SHA-256 and encode to hexadecimal.
     * @param password String
     * @return String
     */
    @Override
    public String encryptPassword(String password){
        StringBuilder hexStringPass = new StringBuilder();

        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = algorithm.digest(password.getBytes(StandardCharsets.UTF_8));

            for (byte b : messageDigest) {
                hexStringPass.append(String.format("%02X", 0xFF & b));
            }
            return hexStringPass.toString();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return password;
    }

    /**
     * This method is used to compare two passwords. One is input from user, second is output of existing user.
     * @param password String
     * @param userFromQuery User object
     * @return boolean
     */
    @Override
    public boolean comparePassword(String password, User userFromQuery){
        password = encryptPassword(password);

        return userFromQuery.getPassword().equals(password);
    }

    @Override
    public void register(User user, JSONObject jsonObject) {
        if(this.isValidField(user)){
            if(this.existByEmail(user.getEmail()))
                jsonObject.put("valid", false);

            else{
                this.encryptPassword(user);
                userRepository.save(user);
                jsonObject.put("valid", true);
            }
        }
        else
            jsonObject.put("valid", false);
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
