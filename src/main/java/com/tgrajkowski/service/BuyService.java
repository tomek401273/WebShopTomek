package com.tgrajkowski.service;

import com.tgrajkowski.model.OrderStatus;
import com.tgrajkowski.model.model.dao.*;
import com.tgrajkowski.model.bucket.Bucket;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.bought.ProductBought;
import com.tgrajkowski.model.product.bucket.ProductBucket;
import com.tgrajkowski.model.product.order.ProductsOrder;
import com.tgrajkowski.model.product.order.ProductsOrderDto;
import com.tgrajkowski.model.product.order.ProductsOrderMapper;
import com.tgrajkowski.model.product.order.Status;
import com.tgrajkowski.model.shipping.ShippingAddress;
import com.tgrajkowski.model.shipping.ShippingAddressDto;
import com.tgrajkowski.model.shipping.ShippingAddressMapper;
import com.tgrajkowski.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
@Slf4j
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
    private ProductBucketDao productBucketDao;

    @Autowired
    private StatusDao statusDao;

    private ProductsOrderMapper productsOrderMapper = new ProductsOrderMapper();

    private ShippingAddressMapper shippingAddressMapper = new ShippingAddressMapper();



    public Long buyAllProductInBucket(ShippingAddressDto shippingAddressDto) {
        int orderValue = 0;
        int totalAmount = 0;
        Long productOrderId;

        Status status = statusDao.findByCode("booked");

        ShippingAddress shippingAddress = shippingAddressMapper.mappToShippedAdderss(shippingAddressDto);
        User user = userDao.findByLogin(shippingAddressDto.getLogin());
        Bucket bucket = bucketDao.findByUser_Id(user.getId());
        List<ProductBucket> products = bucket.getProductBuckets();

        ProductsOrder productsOrder = new ProductsOrder(user);
        productsOrderDao.save(productsOrder);
        productOrderId = productsOrder.getId();

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
        productsOrder.setStatus(status);
        productsOrderDao.save(productsOrder);
        cleaningBucket(products, bucket);
        return productOrderId;
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
        List<ProductsOrder> productsOrders = new ArrayList<>();
        try{
            productsOrders=productsOrderDao.findByUser_Id(user.getId());
        } catch (NullPointerException e) {
           log.error(e.getMessage());
        }
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

    public boolean paymentVerification(OrderStatus paymentDto) {
        Status status = statusDao.findByCode("paid");
        if (paymentDto.isPaid()) {
            ProductsOrder productsOrder = productsOrderDao.findOne(paymentDto.getOrderId());
            productsOrder.setStatus(status);
            productsOrderDao.save(productsOrder);
        }
        return productsOrderDao.findOne(paymentDto.getOrderId()).getStatus().getCode().equals("paid");
    }

    public boolean orderPrepared(OrderStatus orderStatus) {
        Status status = statusDao.findByCode("prepared");
        if (orderStatus.isPrepared()) {
            ProductsOrder productsOrder = productsOrderDao.findOne(orderStatus.getOrderId());
            productsOrder.setStatus(status);
            productsOrderDao.save(productsOrder);
        }

        return productsOrderDao.findOne(orderStatus.getOrderId()).getStatus().getCode().equals("prepared");
    }

    public boolean sendOrder(OrderStatus orderStatus) {
        Status status = statusDao.findByCode("send");

        if (orderStatus.isSend()) {
            ProductsOrder productsOrder = productsOrderDao.findOne(orderStatus.getOrderId());
            productsOrder.setStatus(status);
            productsOrder.setLinkDelivery(orderStatus.getLinkDelivery());
            productsOrder.setSendDate(new Date());
            productsOrderDao.save(productsOrder);
        }

        return productsOrderDao.findOne(orderStatus.getOrderId()).getStatus().getCode().equals("send");
    }

    public Set<ProductsOrderDto> searchOrderContainsProduct(String title) {
        List<Product> products = productDao.findProductContainstTitleWithLetters(title);
        List<ProductBought> productBoughts = new ArrayList<>();
        List<ProductsOrder> productsOrders = new ArrayList<>();
        Set<ProductsOrderDto> productsOrderDtos = new HashSet<>();

        for (Product product : products) {
            List<ProductBought> productBought = productBoughtDao.findByProductId(product.getId());
            productBoughts.addAll(productBought);
        }
        for (ProductBought productBought : productBoughts) {
            productsOrders.add(productBought.getProductsOrder());
        }

        for (ProductsOrder productsOrder : productsOrders) {
            productsOrderDtos.add(productsOrderMapper.mapToProductsOrderDto(productsOrder));
        }
        return productsOrderDtos;
    }

    public List<ProductsOrderDto> filterOrderState(String state) {
        Status status = statusDao.findByCode(state);
        return productsOrderMapper.mapToProductsOrderDtoList(productsOrderDao.findByStatusId(status.getId()));
    }

    public List<ProductsOrderDto> filterOrderDate(String dateAfter, String dateBefore) {
        List<ProductsOrderDto> productsOrderDtos = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateAfterFomated;
        Date dateBeforeFormated;
        try {
            dateAfterFomated = dateFormat.parse(dateAfter);
            dateBeforeFormated= dateFormat.parse(dateBefore);
            productsOrderDtos = productsOrderMapper
                    .mapToProductsOrderDtoList
                            (productsOrderDao.findByBoughtDateAfterAndBoughtDateBefore(dateAfterFomated, dateBeforeFormated));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return productsOrderDtos;
    }
/// przenieść do userDetailService
    public List<String> getAllUser() {
        List<String> userLoginList = new ArrayList<>();
        List<User> userList = userDao.findAll();
        for (User user : userList) {
            userLoginList.add(user.getLogin());
        }
        return userLoginList;
    }
}
