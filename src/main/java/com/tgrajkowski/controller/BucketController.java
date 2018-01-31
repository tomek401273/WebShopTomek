package com.tgrajkowski.controller;

import com.tgrajkowski.model.Bucket;
import com.tgrajkowski.model.Product;
import com.tgrajkowski.model.model.dao.BucketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bucket")
@CrossOrigin("*")
public class BucketController {
    @Autowired
    BucketDao bucketDao;

    @RequestMapping("/all")
    public @ResponseBody
    List<Bucket> getProduct() {
        return bucketDao.findAll();
    }
}
