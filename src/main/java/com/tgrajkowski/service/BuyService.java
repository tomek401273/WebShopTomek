package com.tgrajkowski.service;

import com.tgrajkowski.model.model.dao.*;
import com.tgrajkowski.model.product.Bucket;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.ProductBought;
import com.tgrajkowski.model.product.ProductsOrder;
import com.tgrajkowski.model.product.bucket.ProductBucket;
import com.tgrajkowski.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuyService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductBoughtDao productBoughtDao;

    @Autowired
    private ProductsOrderDao productsOrderDao;

    @Autowired
    private BucketDao bucketDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BucketService bucketService;

    @Autowired
    private ProductBucketDao productBucketDao;

    public void simpleTest() {
        Product product = productDao.findById((long) 1);
        User user = userDao.findByLogin("thomas");
        ProductsOrder productsOrder = new ProductsOrder(1, true, true, false);
        productsOrder.setUser(user);
        productsOrderDao.save(productsOrder);

        ProductBought productBought = new ProductBought(product, productsOrder, 12);
//        productsOrder.getProductBoughts().add(productBought);
        productBoughtDao.save(productBought);
    }


    public void buyAllProductInBucket(String login) {
        int orderValue = 0;
        int totalAmount = 0;

        User user = userDao.findByLogin(login);
        Bucket bucket = bucketDao.findByUser_Id(user.getId());
        List<ProductBucket> products = bucket.getProductBuckets();

        ProductsOrder productsOrder = new ProductsOrder(user);
        productsOrderDao.save(productsOrder);


        for (ProductBucket productBucket : products) {
            Product product = productBucket.getProduct();
            ProductBought productBought = new ProductBought(product, productsOrder, productBucket.getAmount());

            System.out.println("product: totalPrice:" + product.getPrice());
            System.out.println("productBucket amount: " + productBucket.getAmount());
            System.out.println("productBought totalPrice: " + productBought.getTotalPrice() + "productBought amount: " + productBought.getAmount());

            orderValue += productBought.getTotalPrice();
            totalAmount += productBought.getAmount();
            productBoughtDao.save(productBought);
        }

        System.out.println("order total Amount: " + totalAmount);
        productsOrder.setTotalValue(orderValue);
        productsOrder.setTotalAmount(totalAmount);
        productsOrderDao.save(productsOrder);
        cleaningBucket(products, bucket);
    }

    public void cleaningBucket(List<ProductBucket> productBuckets, Bucket bucket) {
        bucket.setProductBuckets(new ArrayList<>());
        bucketDao.save(bucket);

        for (ProductBucket productBucket : productBuckets) {
            Product product = productBucket.getProduct();
            product.getProductBuckets().remove(productBucket);
            productDao.save(product);
            productBucketDao.delete(productBucket);
        }

    }


}
