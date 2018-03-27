package com.tgrajkowski.controller;

import com.tgrajkowski.model.bucket.UserBucketDto;
import com.tgrajkowski.model.product.Bucket;
import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.product.bucket.ProductBucketDto;
import com.tgrajkowski.service.BucketService;
import com.tgrajkowski.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bucket")
@CrossOrigin("*")
public class BucketController {
    @Autowired
    BucketDao bucketDao;
    @Autowired
    BucketService bucketService;

    @Autowired
    BuyService buyService;

    @RequestMapping("/all")
    public @ResponseBody
    List<Bucket> getProduct() {
        return bucketDao.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public boolean addProductToBucket(@RequestBody UserBucketDto userBucketDto) {
       return bucketService.addProductToBucket(userBucketDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/removeAllProductFromBucket")
    public void removeAllProductFromBucket(@RequestBody String loging) {
        bucketService.removeProductFromBucket(loging);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllProductFromBucket")
    public List<ProductBucketDto> getAllProductFromBucket(@RequestParam String login) {
        return bucketService.showProductInBucket(login);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/addList")
    public void addProductToBucketList(@RequestBody UserBucketDto userBucketDto) {
        bucketService.addProductToBucketList(userBucketDto);
    }
    @RequestMapping(method = RequestMethod.PUT, value = "/removeSingleProduct")
    public void removeSingleProductFromBucket(@RequestBody UserBucketDto userBucketDto) {
        bucketService.removeSingleProductFromBucket(userBucketDto);
    }
    @RequestMapping(method = RequestMethod.PUT, value = "/removeSingeItemFromBucket")
    public boolean removeSingleItemFromBucket(@RequestBody UserBucketDto userBucketDto) {
      return bucketService.removeSinggleItemFromBucket(userBucketDto);
    }
}
