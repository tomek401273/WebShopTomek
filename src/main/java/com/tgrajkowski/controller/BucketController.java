package com.tgrajkowski.controller;

import com.tgrajkowski.model.bucket.ProductBucketDto;
import com.tgrajkowski.model.bucket.UserBucketDto;
import com.tgrajkowski.model.product.Bucket;
import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.product.ProductDto;
import com.tgrajkowski.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/bucket")
@CrossOrigin("*")
public class BucketController {
    @Autowired
    BucketDao bucketDao;

    @Autowired
    DbService dbService;

    @RequestMapping("/all")
    public @ResponseBody
    List<Bucket> getProduct() {
        return bucketDao.findAll();
    }

//    @RequestMapping(method = RequestMethod.POST, value = "save-all")
//    public @ResponseBody
//    List<Product>

    @RequestMapping(method = RequestMethod.PUT, value = "/add")
    public boolean addProductToBucket(@RequestBody UserBucketDto userBucketDto) {
        boolean success = dbService.addProductToBucket(userBucketDto);
        System.out.println("bucket controller adding product to bucket was success: " + success);
        return success;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/removeAllProductFromBucket")
    public void removeAllProductFromBucket(@RequestBody String loging) {
        dbService.removeProductFromBucket(loging);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/getAllProductFromBucket")
    public Set<ProductBucketDto> getAllProductFromBucket(@RequestBody String login) {
        return dbService.showProductInBucket(login);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/addList")
    public void addProductToBucketList(@RequestBody UserBucketDto userBucketDto) {
        System.out.println("addProductToBucketList");
        dbService.addProductToBucketList(userBucketDto);
    }

}
