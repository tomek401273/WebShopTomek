package com.tgrajkowski.controller;

import com.tgrajkowski.model.Product;
import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController()
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    ProductDao productDao;

    @Autowired
    DbService dbService;

    @RequestMapping("/all")
    public @ResponseBody
    List<Product> getProduct() {
        return productDao.findAll();
    }

    @RequestMapping("/{id}")
    public @ResponseBody
    Product getProduct(@PathVariable("id") String id) {
        System.out.println("Identyfikator: " + id);
        Long pasedId = Long.valueOf(id);
        return productDao.findOne(pasedId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = APPLICATION_JSON_VALUE)
    public void createProduct(@RequestBody Product product) {
        dbService.saveProduct(product);

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/deleteProduct")
    public void deleteProduct(@RequestBody Product product) {
        productDao.delete(product);
    }

}

