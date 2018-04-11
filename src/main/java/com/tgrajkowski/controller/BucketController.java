package com.tgrajkowski.controller;

import com.tgrajkowski.model.bucket.UserBucketDto;
import com.tgrajkowski.model.bucket.Bucket;
import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.product.bucket.ProductBucketDto;
import com.tgrajkowski.model.user.UserDto;
import com.tgrajkowski.service.BucketService;
import com.tgrajkowski.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bucket")
@CrossOrigin("*")
public class BucketController {

    @Autowired
    BucketService bucketService;

    @Autowired
    BuyService buyService;

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public boolean addProductToBucket(@RequestBody UserBucketDto userBucketDto) {
       return bucketService.addProductToBucket(userBucketDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllProductFromBucket")
    public List<ProductBucketDto> getAllProductFromBucket(@RequestParam String login) {
        return bucketService.showProductInBucket(login);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/addList")
    public void addProductToBucketList(@RequestBody UserBucketDto userBucketDto) {
        bucketService.addProductToBucketList(userBucketDto);
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/removeSingleProduct")
    public void removeSingleProductFromBucket(@RequestParam String login, @RequestParam Long productId) {
        bucketService.removeSingleProductFromBucket(login, productId);
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/removeSingeItemFromBucket")
    public boolean removeSingleItemFromBucket(@RequestParam String login, @RequestParam Long productId) {
      return bucketService.removeSinggleItemFromBucket(login, productId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/addressShipping")
    public UserDto getAddressShippig(String login) {
      return bucketService.getAddressShipping(login);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/coupon")
    public @ResponseBody boolean checkCouponAvailable(@RequestParam String code) {
       return bucketService.checkCodeAvailable(code);
    }

}