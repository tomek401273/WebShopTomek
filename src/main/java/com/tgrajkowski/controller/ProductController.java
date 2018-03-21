package com.tgrajkowski.controller;

import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.model.dao.ProductBucketDao;
import com.tgrajkowski.model.product.*;
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


    @RequestMapping(method = RequestMethod.PUT, value = "/available")
    public int checkAvaiable(@RequestBody Long id) {
        return productService.checkAvailable(id);
    }

    @RequestMapping("/all")
    public @ResponseBody
    List<ProductDto> getProduct() {
        return productService.getProducts();
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

    @RequestMapping(method = RequestMethod.PUT, value = "/deleteProduct")
    public void deleteProduct(@RequestBody ProductDto productDto) {
        productService.removeProductFromDatabase(productDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateProduct")
    public void updateProduct(@RequestBody @Valid ProductDto productDto) {
        productService.updateProduct(productDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/searchProduct")
    public @ResponseBody
    List<ProductDto> searchProduct(@RequestBody String title) {
        return productService.searchProduct(title);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/filterPrice")
    public @ResponseBody
    List<ProductDto> filterPrice(@RequestBody FilterPrice filterPrice) {
        return productService.filterProductWithPriceBetween(filterPrice);
    }
}

