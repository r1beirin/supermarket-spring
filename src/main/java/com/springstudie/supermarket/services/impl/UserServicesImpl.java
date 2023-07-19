package com.springstudie.supermarket.services.impl;

import com.springstudie.supermarket.entity.User;
import com.springstudie.supermarket.repository.UserRepository;
import com.springstudie.supermarket.services.UserServices;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import java.util.Optional;

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

    @Override
    public void login(String email, String password, JSONObject jsonObject) {
        Optional<User> user = userRepository.findByEmail(email);
        if(this.existByEmail(email) && this.comparePassword(password, user.get())){
            jsonObject.put("valid", true);
        }
        else{
            jsonObject.put("valid", false);
        }
    }
}
