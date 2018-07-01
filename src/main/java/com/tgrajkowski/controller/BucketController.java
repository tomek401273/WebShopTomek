package com.tgrajkowski.controller;

import com.tgrajkowski.model.bucket.UserBucketDto;
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
    public List<ProductBucketDto> getAllProductFromBucket() {
        return bucketService.showProductInBucket();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/addList")
    public void addProductToBucketList(@RequestBody UserBucketDto userBucketDto) {
        bucketService.addProductToBucketList(userBucketDto);
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/removeSingleProduct")
    public void removeSingleProductFromBucket(@RequestParam Long productId) {
        bucketService.removeSingleProductFromBucket(productId);
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/removeSingeItemFromBucket")
    public boolean removeSingleItemFromBucket(@RequestParam Long productId) {
      return bucketService.removeSinggleItemFromBucket(productId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/addressShipping")
    public UserDto getAddressShippig() {
      return bucketService.getAddressShipping();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/coupon")
    public @ResponseBody boolean checkCouponAvailable(@RequestParam String code) {
       return bucketService.checkCodeAvailable(code);
    }

    @RequestMapping(method = RequestMethod.GET, value = "count")
    public @ResponseBody int getCountProductInBucket() {
        return bucketService.countProductInBucket();
    }

}