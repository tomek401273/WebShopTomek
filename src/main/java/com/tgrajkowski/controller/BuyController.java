package com.tgrajkowski.controller;

import com.tgrajkowski.model.product.bucket.ProductBucketDto;
import com.tgrajkowski.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buy")
@CrossOrigin("*")
public class BuyController {

    @Autowired
    BuyService buyService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public void simpleTest() {
        buyService.simpleTest();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/buy")
    public void buyAllProductInBucket(@RequestBody String login) {
        buyService.buyAllProductInBucket(login);
    }


}
