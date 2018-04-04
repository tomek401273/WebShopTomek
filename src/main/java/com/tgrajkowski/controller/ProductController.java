package com.tgrajkowski.controller;

import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.model.dao.ProductBucketDao;
import com.tgrajkowski.model.product.*;
import com.tgrajkowski.model.product.reminder.ProductEmailReminderDto;
import com.tgrajkowski.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController()
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    ProductDao productDao;

    @Autowired
    BucketDao bucketDao;

    @Autowired
    ProductService productService;

    @Autowired
    ProductBucketDao product_bucketDao;

    @RequestMapping(method = RequestMethod.GET, value = "/available")
    public int checkAvaiable(@RequestParam Long id) {
        return productService.checkAvailable(id);
    }

    @RequestMapping("/all")
    public @ResponseBody
    List<ProductDto> getProduct() {
        return productService.getProducts();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllProductToEdit")
    public @ResponseBody
    List<ProductDto> getAllProductToEdit() {
        return productService.getProductsToEdit();
    }

    @RequestMapping("/{id}")
    public @ResponseBody
    ProductDto getProduct(@PathVariable("id") String id) {
        Long pasedId = Long.valueOf(id);
        return productService.getOneProduct(pasedId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = APPLICATION_JSON_VALUE)
    public void createProduct(@RequestBody @Valid ProductDto productDto) {
        productService.saveProduct(productDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteProduct")
    public void deleteProduct(@RequestParam  Long id) throws InterruptedException {
        productService.removeProductFromDatabase(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateProduct")
    public void updateProduct(@RequestBody @Valid ProductDto productDto) {
        productService.updateProduct(productDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/searchProduct")
    public @ResponseBody
    List<ProductDto> searchProduct(@RequestParam String title) {
        return productService.searchProduct(title);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/filterPrice")
    public @ResponseBody
    List<ProductDto> filterPrice(@RequestParam int above, @RequestParam int below) {
        return productService.filterProductWithPriceBetween(above, below);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllProductsTitle")
    public @ResponseBody
    List<String> getAllProductsTitle() {
        return productService.getAllProductsTitle();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/setReminder")
    public void setReminder(@RequestBody ProductEmailReminderDto productEmailReminderDto) {
        productService.setReminder(productEmailReminderDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/maxprice")
    public @ResponseBody int getMaxPriceProduct () {
        return productService.maxPriceProduct();
    }
}

