package com.tgrajkowski.service;

import com.tgrajkowski.model.OrderStatus;
import com.tgrajkowski.model.location.response.view.result.location.address.AddressDto;
import com.tgrajkowski.model.model.dao.*;
import com.tgrajkowski.model.bucket.Bucket;
import com.tgrajkowski.model.newsletter.Subscriber;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.bought.ProductBought;
import com.tgrajkowski.model.product.bucket.ProductBucket;
import com.tgrajkowski.model.product.order.*;
import com.tgrajkowski.model.shipping.ShippingAddress;
import com.tgrajkowski.model.shipping.ShippingAddressDto;
import com.tgrajkowski.model.shipping.ShippingAddressMapper;
import com.tgrajkowski.model.user.Users;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
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

    @Autowired
    private ShippingAddressMapper shippingAddressMapper;

//    private ShippingAddressMapper shippingAddressMapper = new ShippingAddressMapper();

    @Autowired
    private SubscriberDao subscriberDao;

    @Autowired
    private LocationService locationService;


    public ProductsOrderDto buyAllProductInBucket(ShippingAddressDto shippingAddressDto) {
        BigDecimal orderValue = BigDecimal.ZERO;
        int totalAmount = 0;
        boolean isCodeCorrect = false;
        if (!shippingAddressDto.getCode().equals("null")) {
            Subscriber subscriber = subscriberDao.findByCode(shippingAddressDto.getCode());
            if (subscriber != null) {
                subscriber.setCode(null);
                subscriberDao.save(subscriber);
                isCodeCorrect = true;
            }
        }
        Status status = statusDao.findByCode("booked");
        AddressDto addressDto = locationService.searchLocation(shippingAddressDto.getSearch());
        if (addressDto == null) {
            return null;
        }
        ShippingAddress shippingAddress = shippingAddressMapper.mapToShippingAddresFromAddressDto(addressDto, shippingAddressDto);
        Users user = userDao.findByLogin(shippingAddressDto.getLogin());
        Bucket bucket = bucketDao.findByUser_Id(user.getId());
        List<ProductBucket> products = bucket.getProductBuckets();

        ProductsOrder productsOrder = new ProductsOrder(user);
        productsOrderDao.save(productsOrder);

        for (ProductBucket productBucket : products) {
            Product product = productBucket.getProduct();
            ProductBought productBought = new ProductBought(product, productsOrder, productBucket.getAmount());
            orderValue = orderValue.add(productBought.getProduct().getPrice().multiply(new BigDecimal(productBought.getAmount())));
            totalAmount += productBought.getAmount();
            productBoughtDao.save(productBought);
        }
        if (isCodeCorrect) {
            BigDecimal discountDecimal = new BigDecimal("0.1");
            orderValue = orderValue.subtract(orderValue.multiply(discountDecimal));
        }
        productsOrder.setTotalValue(orderValue);
        productsOrder.setTotalAmount(totalAmount);
        productsOrder.setShippingAddress(shippingAddress);
        productsOrder.setStatus(status);
        productsOrderDao.save(productsOrder);
        cleaningBucket(products, bucket);
        ProductsOrderDto productsOrderDto = productsOrderMapper.mapToProductsOrderDto(productsOrder);
        return productsOrderDto;
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
        Users user = userDao.findByLogin(login);
        List<ProductsOrder> productsOrders = new ArrayList<>();
        try {
            productsOrders = productsOrderDao.findByUser_Id(user.getId());
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        }
        System.out.println("Product products size: " + productsOrders.size());
        return productsOrderMapper
                .mapToProductsOrderDtoList
                        (productsOrders);
    }

    public ProductsOrderDto getProductsOrder(Long id) {
        Optional<ProductsOrder> productsOrder = Optional.ofNullable(productsOrderDao.findOne(id));
        if (productsOrder.isPresent()) {
            return productsOrderMapper.mapToProductsOrderDto(productsOrder.get());
        }
        return null;
    }

    public List<ProductsOrderDto> getAllOrders() {
        return productsOrderMapper
                .mapToProductsOrderDtoList
                        (productsOrderDao.findAll());
    }

    public boolean paymentVerification(OrderStatus paymentDto) {
        if (paymentDto.getStatus().equals("paid")) {
            Status status = statusDao.findByCode("paid");
            ProductsOrder productsOrder = productsOrderDao.findOne(paymentDto.getOrderId());
            productsOrder.setStatus(status);
            productsOrderDao.save(productsOrder);
        }
        return productsOrderDao.findOne(paymentDto.getOrderId()).getStatus().getCode().equals("paid");
    }

    public boolean orderPrepared(OrderStatus orderStatus) {
        Status status = statusDao.findByCode("prepared");
        if (orderStatus.getStatus().equals("prepared")) {
            System.out.println("Order prepared work correctly");
            ProductsOrder productsOrder = productsOrderDao.findOne(orderStatus.getOrderId());
            productsOrder.setStatus(status);
            productsOrderDao.save(productsOrder);
        }

        System.out.println("Return statement: " + productsOrderDao.findOne(orderStatus.getOrderId()).getStatus().getCode().equals("prepared"));
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

    @Transactional
    public void orderDeliver() {
        Status send = statusDao.findByCode("send");
        Status delivered = statusDao.findByCode("delivered");
        List<ProductsOrder> sendOrders = productsOrderDao.findByStatusId(send.getId());
        for (ProductsOrder productsOrder : sendOrders) {
            productsOrder.setDeliveredDate(new Date());
            productsOrder.setStatus(delivered);
        }
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
        Date dateA = null;
        Date dateB = null;
        dateAfter = dateAfter + " 00:00:00";
        dateBefore = dateBefore + " 23:59:59";

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        try {
            dateA = dateFormat.parse(dateAfter);
            dateB = dateFormat.parse(dateBefore);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return productsOrderMapper.mapToProductsOrderDtoList(
                productsOrderDao.findByBoughtDateBetween(dateA, dateB));
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
