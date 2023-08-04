package com.springstudie.supermarket.controller;

import com.springstudie.supermarket.services.UserServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    private final UserServices userServices;

    public IndexController(UserServices userServices){
        this.userServices = userServices;
    }

    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");

        modelAndView.addObject("isLogged", userServices.isAuthenticated());

        return modelAndView;
    }
}
