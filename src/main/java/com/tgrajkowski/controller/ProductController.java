package com.tgrajkowski.controller;

import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.model.dao.Product_BucketDao;
import com.tgrajkowski.model.product.Bucket;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.ProductDto;
import com.tgrajkowski.model.product.Product_Bucket;
import com.tgrajkowski.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    Product_BucketDao product_bucketDao;


    @RequestMapping(method = RequestMethod.PUT, value = "/available")
    public int checkAvaiable(@RequestBody Long id) {
        return productService.checkAvailable(id);
    }

    @RequestMapping("/all")
    public @ResponseBody
    List<ProductDto> getProduct() {
        save();
        return productService.getProducts();
    }

    @RequestMapping("/{id}")
    public @ResponseBody
    ProductDto getProduct(@PathVariable("id") String id) {
        Long pasedId = Long.valueOf(id);
        return productService.getOneProduct(pasedId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save", consumes = APPLICATION_JSON_VALUE)
    public void createProduct(@RequestBody ProductDto productDto) {
        productService.saveProduct(productDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/deleteProduct")
    public void deleteProduct(@RequestBody ProductDto productDto) {
        productService.removeProductFromDatabase(productDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateProduct")
    public void updateProduct(@RequestBody ProductDto productDto) {
        productService.updateTask(productDto);
    }

    public void save() {
        Product product = productDao.findById((long) 109);
        Bucket bucket = bucketDao.findById((long) 2);
        Product_Bucket product_bucket = new Product_Bucket(product, bucket);
        product_bucket.setAmount(22);
//        product_bucket.setBucket(bucket);
//        product_bucket.setProduct(product);
//        List<Product_Bucket> productBuckets = new ArrayList<>();
//        productBuckets.add(product_bucket);
//        product.getProductBuckets().add(product_bucket);
//        bucket.getProductBuckets().add(product_bucket);

        product_bucketDao.save(product_bucket);

    }
}

