package com.springstudie.supermarket.controller;

import com.springstudie.supermarket.model.usecases.Product;
import com.springstudie.supermarket.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {
    private final ProductRepository productRepository;

    private final ProductsAPIController productAPI;

    public ProductController(ProductRepository productRepository, ProductsAPIController productAPI1) {
        this.productRepository = productRepository;
        this.productAPI = productAPI1;
    }
    @GetMapping("/products")
    public ModelAndView products(){
        ModelAndView mv = new ModelAndView("products");
        List<Product> productList = productAPI.getAllProducts();
        System.out.println(productList);
        //mv.addObject(productList);

        return mv;
    }

    @RequestMapping(value = "/products/register", method = RequestMethod.GET)
    public String registerProduct(Model model){
        model.addAttribute("productsRegister");

        return "productsRegister";
    }
}
