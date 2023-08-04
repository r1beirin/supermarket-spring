package com.springstudie.supermarket.controller;

import com.springstudie.supermarket.entity.User;
import com.springstudie.supermarket.services.impl.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("index");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isLogged = authentication.getPrincipal() instanceof UserDetailsImpl;

        modelAndView.addObject("isLogged", isLogged);

        return modelAndView;
    }
}
