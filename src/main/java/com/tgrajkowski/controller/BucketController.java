package com.tgrajkowski.controller;

import com.tgrajkowski.model.bucket.UserBucketDto;
import com.tgrajkowski.model.product.Bucket;
import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.product.ProductBucketDto;
import com.tgrajkowski.model.user.User;
import com.tgrajkowski.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/bucket")
@CrossOrigin("*")
public class BucketController {
    @Autowired
    BucketDao bucketDao;
    @Autowired
    BucketService bucketService;

    @RequestMapping("/all")
    public @ResponseBody
    List<Bucket> getProduct() {
        return bucketDao.findAll();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/add")
    public boolean addProductToBucket(@RequestBody UserBucketDto userBucketDto) {
       return bucketService.addProductToBucket(userBucketDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/removeAllProductFromBucket")
    public void removeAllProductFromBucket(@RequestBody String loging) {
        bucketService.removeProductFromBucket(loging);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/getAllProductFromBucket")
    public List<ProductBucketDto> getAllProductFromBucket(@RequestBody String login) {
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
    public void removeSingleItemFromBucket(@RequestBody UserBucketDto userBucketDto) {
         bucketService.removeSinggleItemFromBucket(userBucketDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getList")
    public UserBucketDto getLongsList() {
        UserBucketDto userBucketDto = new UserBucketDto((long)1,"thomas");


        List<Long> longList= new ArrayList<>();
        longList.add((long)1);
        longList.add((long)2);
        longList.add((long)3);
        userBucketDto.setProductIdArray(longList);

        return userBucketDto;
    }

}
