package com.tgrajkowski.service;

import com.tgrajkowski.model.PaymentDto;
import com.tgrajkowski.model.model.dao.*;
import com.tgrajkowski.model.product.Bucket;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.bought.ProductBought;
import com.tgrajkowski.model.product.order.ProductsOrder;
import com.tgrajkowski.model.product.bucket.ProductBucket;
import com.tgrajkowski.model.product.order.ProductsOrderDto;
import com.tgrajkowski.model.product.order.ProductsOrderMapper;
import com.tgrajkowski.model.shipping.ShippingAddress;
import com.tgrajkowski.model.shipping.ShippingAddressDto;
import com.tgrajkowski.model.shipping.ShippingAddressMapper;
import com.tgrajkowski.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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

    private ProductsOrderMapper productsOrderMapper = new ProductsOrderMapper();

    private ShippingAddressMapper shippingAddressMapper = new ShippingAddressMapper();

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


    public void buyAllProductInBucket(ShippingAddressDto shippingAddressDto) {
        int orderValue = 0;
        int totalAmount = 0;
        ShippingAddress shippingAddress = shippingAddressMapper.mappToShippedAdderss(shippingAddressDto);
        User user = userDao.findByLogin(shippingAddressDto.getLogin());
        Bucket bucket = bucketDao.findByUser_Id(user.getId());
        List<ProductBucket> products = bucket.getProductBuckets();

        ProductsOrder productsOrder = new ProductsOrder(user);
        productsOrderDao.save(productsOrder);


        for (ProductBucket productBucket : products) {
            Product product = productBucket.getProduct();
            ProductBought productBought = new ProductBought(product, productsOrder, productBucket.getAmount());

            orderValue += productBought.getTotalPrice();
            totalAmount += productBought.getAmount();
            productBoughtDao.save(productBought);
        }

        productsOrder.setTotalValue(orderValue);
        productsOrder.setTotalAmount(totalAmount);
        productsOrder.setShippingAddress(shippingAddress);
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

    public List<ProductsOrderDto> getAllProductsOrdersUser(String login) {
        User user = userDao.findByLogin(login);
        List<ProductsOrder> productsOrders = productsOrderDao.findByUser_Id(user.getId());

        return productsOrderMapper
                .mapToProductsOrderDtoList
                        (productsOrders);
    }

    public ProductsOrderDto getProductsOrder(Long id) {
        return productsOrderMapper.mapToProductsOrderDto(productsOrderDao.findOne(id));
    }

    public List<ProductsOrderDto> getAllOrders() {
        return productsOrderMapper
                .mapToProductsOrderDtoList
                        (productsOrderDao.findAll());
    }

    public boolean paymentVerification(PaymentDto paymentDto) {
        boolean isPaid;
        ProductsOrder productsOrder = productsOrderDao.findOne(paymentDto.getOrderId());
        productsOrder.setPaid(paymentDto.isPaid());
        productsOrderDao.save(productsOrder);
        ProductsOrder productsOrder2 = productsOrderDao.findOne(paymentDto.getOrderId());
        isPaid= productsOrder2.isPaid();
        System.out.println("order order is paid ???: "+isPaid);
        return isPaid;
    }
}
