package com.springstudie.supermarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProductController {

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String seeProducts(Model model){
        model.addAttribute("products");

        return "products";
    }

    @RequestMapping(value = "/products/register", method = RequestMethod.GET)
    public String registerProduct(Model model){
        model.addAttribute("registerProduct");

        return "registerProduct";
    }
}
