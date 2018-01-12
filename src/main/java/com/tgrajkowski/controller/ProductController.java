package com.tgrajkowski.controller;

import com.tgrajkowski.model.Product;
import com.tgrajkowski.model.model.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/product")
@CrossOrigin
public class ProductController {
    @Autowired
    ProductDao productDao;
    @CrossOrigin
    @RequestMapping("/id")
    public Product getProduct(@RequestParam Long id) {
        return productDao.findOne(id);
    }
}

