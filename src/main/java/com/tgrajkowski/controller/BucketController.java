package com.tgrajkowski.controller;

import com.tgrajkowski.model.Bucket;
import com.tgrajkowski.model.Product;
import com.tgrajkowski.model.model.dao.BucketDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buck")
@CrossOrigin("*")
public class BucketController {
    @Autowired
    BucketDao bucketDao;

    @RequestMapping("/")
    public @ResponseBody
    List<Bucket> getProduct() {
        return bucketDao.findAll();
    }
}
