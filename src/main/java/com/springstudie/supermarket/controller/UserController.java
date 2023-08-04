package com.springstudie.supermarket.controller;

import com.springstudie.supermarket.entity.User;
import com.springstudie.supermarket.services.UserServices;
import com.springstudie.supermarket.util.PasswordUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }
    @GetMapping("register")
    public ModelAndView register(){
        ModelAndView mv = new ModelAndView("register");
        mv.addObject("user", new User());
        return mv;
    }

    @PostMapping("register")
    public ModelAndView register(@ModelAttribute User user){
        ModelAndView mv = new ModelAndView("register");

        userServices.register(user);

        mv.addObject("user", user);

        return mv;
    }

    @GetMapping("login")
    public ModelAndView login() { return new ModelAndView("login"); }
}
