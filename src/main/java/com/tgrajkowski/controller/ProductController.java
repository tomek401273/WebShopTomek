package com.tgrajkowski.controller;

import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.product.ProductDto;
import com.tgrajkowski.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping(method = RequestMethod.PUT, value = "/available")
    public int checkAvaiable(@RequestBody Long id) {
        return dbService.checkAvailable(id);
    }


    @RequestMapping("/all")
    public @ResponseBody
    List<ProductDto> getProduct() {
        return dbService.getProducts();
    }


    @RequestMapping("/{id}")
    public @ResponseBody
    Product getProduct(@PathVariable("id") String id) {
        System.out.println("Identyfikator: " + id);
        Long pasedId = Long.valueOf(id);
        return productDao.findOne(pasedId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = APPLICATION_JSON_VALUE)
    public void createProduct(@RequestBody ProductDto productDto) {
        dbService.saveProduct(productDto);

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/deleteProduct")
    public void deleteProduct(@RequestBody Product product) {
        productDao.deleteById(product.getId());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateProduct")
    public void updateProduct(@RequestBody Product product) {
        System.out.println("updadteProduct: " + product.toString());
        productDao.save(product);
    }


}

