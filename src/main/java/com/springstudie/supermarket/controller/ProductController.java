package com.springstudie.supermarket.controller;

import com.springstudie.supermarket.entity.Product;
import com.springstudie.supermarket.services.ProductServices;
import com.springstudie.supermarket.services.UserServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("product")
public class ProductController {

    private final ProductServices productServices;

    public ProductController(ProductServices productServices) {
        this.productServices = productServices;
    }
    @GetMapping("")
    public ModelAndView products(){
        ModelAndView mv = new ModelAndView("products");
        List<Product> productList = productServices.getAllProduct();

        mv.addObject("productList", productList);

        return mv;
    }

    @GetMapping(value = "register")
    public ModelAndView register(){
        ModelAndView mv = new ModelAndView("productsRegister");

        mv.addObject("product", new Product());

        return mv;
    }

    @PostMapping(value = "register")
    public ModelAndView register(@ModelAttribute Product product){
        ModelAndView mv = new ModelAndView("productsRegister");

        productServices.postProduct(product);

        mv.addObject("product", product);

        return mv;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable long id){
        ModelAndView mv = new ModelAndView("productsEdit");
        Product product = productServices.getProduct(id).getBody();

        mv.addObject("product", product);

        return mv;
    }

    @PutMapping("/edit/{id}")
    public ModelAndView editProduct(@ModelAttribute Product product){
        ModelAndView mv = new ModelAndView("productsEdit");

        productServices.updateProduct(product);

        return mv;
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable long id){
        productServices.deleteProduct(id);
        return "redirect:/";
    }
}
