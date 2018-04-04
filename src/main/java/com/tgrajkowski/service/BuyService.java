package com.tgrajkowski.service;

import com.tgrajkowski.model.OrderStatus;
import com.tgrajkowski.model.model.dao.*;
import com.tgrajkowski.model.bucket.Bucket;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.bought.ProductBought;
import com.tgrajkowski.model.product.bucket.ProductBucket;
import com.tgrajkowski.model.product.order.*;
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
import java.util.stream.Collectors;

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

            orderValue += productBought.getAmount()*productBought.getProduct().getPrice();
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
        try {
            productsOrders = productsOrderDao.findByUser_Id(user.getId());
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
        if (paymentDto.getStatus().equals("paid")) {
            ProductsOrder productsOrder = productsOrderDao.findOne(paymentDto.getOrderId());
            productsOrder.setStatus(status);
            productsOrderDao.save(productsOrder);
        }
        return productsOrderDao.findOne(paymentDto.getOrderId()).getStatus().getCode().equals("paid");
    }

    public boolean orderPrepared(OrderStatus orderStatus) {
        Status status = statusDao.findByCode("prepared");
        if (orderStatus.getStatus().equals("prepared")) {
            ProductsOrder productsOrder = productsOrderDao.findOne(orderStatus.getOrderId());
            productsOrder.setStatus(status);
            productsOrderDao.save(productsOrder);
        }

        return productsOrderDao.findOne(orderStatus.getOrderId()).getStatus().getCode().equals("prepared");
    }

    public boolean sendOrder(OrderStatus orderStatus) {
        Status status = statusDao.findByCode("send");

        if (orderStatus.getStatus().equals("send")) {
            ProductsOrder productsOrder = productsOrderDao.findOne(orderStatus.getOrderId());
            productsOrder.setStatus(status);
            productsOrder.setLinkDelivery(orderStatus.getLinkDelivery());
            productsOrder.setSendDate(new Date());
            productsOrderDao.save(productsOrder);
        }
        return productsOrderDao.findOne(orderStatus.getOrderId()).getStatus().getCode().equals("send");
    }

    public boolean orderDelivered(OrderStatus orderStatus) throws InterruptedException {
        Status status = statusDao.findByCode("delivered");

        if (orderStatus.getStatus().equals("delivered")) {
            ProductsOrder productsOrder = productsOrderDao.findOne(orderStatus.getOrderId());
            Thread.sleep(calulateWaitingTime());
            productsOrder.setDeliveredDate(new Date());
            productsOrder.setStatus(status);
            productsOrderDao.save(productsOrder);
        }
        return productsOrderDao.findOne(orderStatus.getOrderId()).getStatus().getCode().equals("delivered");
    }


    public Long calulateWaitingTime() {
        Date dateTomorow = calculateDateShipping();
        Long futureDate = dateTomorow.getTime();
        Long nowDate = System.currentTimeMillis();
        Long calculatedDate = futureDate - nowDate;
        return calculatedDate;
    }

    public Date calculateDateShipping() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 1);
        return calendar.getTime();
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
        if (state.equals("all") || state.equals("null")) {
            return new ArrayList<>();
        }
        Status status = statusDao.findByCode(state);
        return productsOrderMapper.mapToProductsOrderDtoList(productsOrderDao.findByStatusId(status.getId()));
    }

    public List<ProductsOrderDto> filterOrderDate(String dateAfter, String dateBefore) {
        List<ProductsOrderDto> productsOrderDtos = new ArrayList<>();

        String appendixAfter = " 00:00:00";
        String appendixBefore = " 23:59:59";
        dateAfter = dateFormat(dateAfter) + appendixAfter;
        dateBefore = dateFormat(dateBefore) + appendixBefore;

        try {
            productsOrderDtos = productsOrderMapper
                    .mapToProductsOrderDtoList(
                            productsOrderDao.findOrderAfterDate(dateAfter, dateBefore)
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productsOrderDtos;
    }

    public String dateFormat(String data) {
        if (data.length() == 9) {
            data = "0" + data;
        }
        data = data.replaceAll("[.]", "-");
        String day = data.substring(0, 2);
        String month = data.substring(2, 6);
        String year = data.substring(6, data.length());
        data = year + month + day;
        return data;
    }

    public List<ProductsOrderDto> searchOrders(OrderSearch orderSearch) {
        List<ProductsOrderDto> foundOrders = new ArrayList<>();
        if (orderSearch.getProductTitle().length() > 3) {
            Set<ProductsOrderDto> foundOrdersWithTitleSet = searchOrderContainsProduct(orderSearch.getProductTitle());
            for (ProductsOrderDto productsOrderDto : foundOrdersWithTitleSet) {
                foundOrders.add(productsOrderDto);
            }
        }
        if (orderSearch.getDateFrom().length() > 8 && orderSearch.getDateTo().length() > 8) {
            List<ProductsOrderDto> foundOrdersWithDate = filterOrderDate(orderSearch.getDateFrom(), orderSearch.getDateTo());
            foundOrders = filterProductOrders(foundOrders, foundOrdersWithDate);
        }
        if (orderSearch.getStatus().length() > 3) {
            List<ProductsOrderDto> foundState = filterOrderState(orderSearch.getStatus());
            foundOrders = filterProductOrders(foundOrders, foundState);
        }
        if (orderSearch.getUserLogin().length() > 6 && !orderSearch.getUserLogin().equals("undefined")) {
            List<ProductsOrderDto> foundOrdersWithLogin = getAllProductsOrdersUser(orderSearch.getUserLogin());
            foundOrders = filterProductOrders(foundOrders, foundOrdersWithLogin);
        }
        return foundOrders;
    }

    private List<ProductsOrderDto> filterProductOrders(List<ProductsOrderDto> productsOrders1, List<ProductsOrderDto> productsOrders2) {
        List<ProductsOrderDto> commonList = new ArrayList<>();
        if (productsOrders1.size() == 0) {
            return productsOrders2;
        }
        for (ProductsOrderDto productsOrder1 : productsOrders1) {
            for (ProductsOrderDto productsOrder2 : productsOrders2) {
                if (productsOrder1.getId().equals(productsOrder2.getId())) {
                    commonList.add(productsOrder1);
                }
            }
        }

        return commonList;
    }
}
