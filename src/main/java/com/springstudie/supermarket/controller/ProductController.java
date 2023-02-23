package com.springstudie.supermarket.controller;

import com.springstudie.supermarket.model.usecases.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class ProductController {

    private final ProductsAPIController productAPI;

    public ProductController(ProductsAPIController productAPI) {
        this.productAPI = productAPI;
    }
    @GetMapping("/products")
    public ModelAndView allProducts(){
        ModelAndView mv = new ModelAndView("products");
        List<Product> productList = productAPI.getAllProducts();
        mv.addObject("productList", productList);

        return mv;
    }

    @GetMapping(value = "/products/register")
    public ModelAndView registerProduct(){

        return new ModelAndView("productsRegister");
    }

    @GetMapping("/products/edit/{id}")
    public ModelAndView editProduct(@PathVariable long id){
        ModelAndView mv = new ModelAndView("productsEdit");
        Product product = productAPI.getProduct(id).getBody();
        mv.addObject("product", product);
        return mv;
    }
}
