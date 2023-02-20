package com.springstudie.supermarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {

    @RequestMapping("/products")
    public String seeProducts(Model model){
        model.addAttribute("products");

        return "products";
    }
}
