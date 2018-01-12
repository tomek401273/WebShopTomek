package com.tgrajkowski.controller;

import com.tgrajkowski.model.Product;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @RequestMapping("/product")
    @CrossOrigin
    public Product getProduct() {
        Product productP = new Product();
        productP.setDescription("Computer");
        productP.setImageLink("computer");
        productP.setPrice(100000);
        productP.setTitle("Super Computer");
        return productP;
    }
}
