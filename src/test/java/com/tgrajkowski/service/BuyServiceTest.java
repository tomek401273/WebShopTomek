package com.tgrajkowski.service;

import com.tgrajkowski.model.OrderStatus;
import com.tgrajkowski.model.bucket.Bucket;
import com.tgrajkowski.model.location.response.view.result.location.address.AddressDto;
import com.tgrajkowski.model.model.dao.*;
import com.tgrajkowski.model.newsletter.Subscriber;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.ProductStatus;
import com.tgrajkowski.model.product.bought.ProductBought;
import com.tgrajkowski.model.product.bucket.ProductBucket;
import com.tgrajkowski.model.product.order.*;
import com.tgrajkowski.model.shipping.ShippingAddress;
import com.tgrajkowski.model.shipping.ShippingAddressDto;
import com.tgrajkowski.model.shipping.ShippingAddressMapper;
import com.tgrajkowski.model.user.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BuyServiceTest {
    @InjectMocks
    BuyService buyService;

    @Mock
    private ProductDao productDao;

    @Mock
    private ProductBoughtDao productBoughtDao;

    @Mock
    private ProductsOrderDao productsOrderDao;

    @Mock
    private BucketDao bucketDao;

    @Mock
    private UserDao userDao;

    @Mock
    private ProductBucketDao productBucketDao;

    @Mock
    private StatusDao statusDao;

    private ProductsOrderMapper productsOrderMapper = new ProductsOrderMapper();

    @Mock
    private SubscriberDao subscriberDao;

    @Mock
    private LocationService locationService;

    @Mock
    private ShippingAddressMapper shippingAddressMapper;

    private Long idOne = new Long(1);

    @Test
    public void buyAllProductInBucketEmptyBucket() {
        // Given
        ShippingAddressDto shippingAddressDto = new ShippingAddressDto();
        shippingAddressDto.setCode("null");
        shippingAddressDto.setLogin("login");
        Status status = new Status();
        status.setCode("booked");
        when(statusDao.findByCode("booked")).thenReturn(status);
        shippingAddressDto.setSearch("Address");
        AddressDto addressDto = new AddressDto();
        when(locationService.searchLocation(shippingAddressDto.getSearch())).thenReturn(addressDto);
        ShippingAddress shippingAddress = createShippingAddress();
        when(shippingAddressMapper.mapToShippingAddresFromAddressDto(addressDto, shippingAddressDto)).thenReturn(shippingAddress);
        Users user = new Users();
        user.setId(idOne);
        when(userDao.findByLogin(shippingAddressDto.getLogin())).thenReturn(user);
        Bucket bucket = new Bucket();
        when(bucketDao.findByUser_Id(user.getId())).thenReturn(bucket);
        // When
        ProductsOrderDto productsOrderDto = buyService.buyAllProductInBucket(shippingAddressDto);
        // Then
        assertEquals("booked", productsOrderDto.getStatusCode());
        assertEquals(new BigDecimal(0), productsOrderDto.getTotalValue());
        assertEquals(0, productsOrderDto.getTotalAmount());
        assertEquals("Country", productsOrderDto.getShippingAddressDto().getCountry());
        assertEquals("City", productsOrderDto.getShippingAddressDto().getCity());
        assertEquals("PostCode", productsOrderDto.getShippingAddressDto().getPostalCode());
        assertEquals("Name", productsOrderDto.getShippingAddressDto().getName());
        assertEquals("Surname", productsOrderDto.getShippingAddressDto().getSurname());
        assertEquals("Supplier", productsOrderDto.getShippingAddressDto().getSupplier());
        assertEquals("State", productsOrderDto.getShippingAddressDto().getState());
        assertEquals("County", productsOrderDto.getShippingAddressDto().getCounty());
        assertEquals("District", productsOrderDto.getShippingAddressDto().getDistrict());
        assertEquals("Subdistrict", productsOrderDto.getShippingAddressDto().getSubdistrict());
        assertEquals(1, productsOrderDto.getShippingAddressDto().getHouse());
        assertEquals(2, productsOrderDto.getShippingAddressDto().getApartment());
    }

    @Test
    public void buyAllProductInBucketOneItemInBucket() {
        // Given
        ShippingAddressDto shippingAddressDto = new ShippingAddressDto();
        shippingAddressDto.setCode("null");
        shippingAddressDto.setLogin("login");
        Status status = new Status();
        status.setCode("booked");
        when(statusDao.findByCode("booked")).thenReturn(status);
        shippingAddressDto.setSearch("Address");
        AddressDto addressDto = new AddressDto();
        when(locationService.searchLocation(shippingAddressDto.getSearch())).thenReturn(addressDto);
        ShippingAddress shippingAddress = createShippingAddress();
        when(shippingAddressMapper.mapToShippingAddresFromAddressDto(addressDto, shippingAddressDto)).thenReturn(shippingAddress);
        Users user = new Users();
        user.setId(idOne);
        when(userDao.findByLogin(shippingAddressDto.getLogin())).thenReturn(user);

        Product product = createProduct();
        ProductBucket productBucket = new ProductBucket();
        productBucket.setProduct(product);
        productBucket.setAmount(1);
        Bucket bucket = new Bucket();
        bucket.getProductBuckets().add(productBucket);
        when(bucketDao.findByUser_Id(user.getId())).thenReturn(bucket);
        // When
        ProductsOrderDto productsOrderDto = buyService.buyAllProductInBucket(shippingAddressDto);
        // Then
        assertEquals("booked", productsOrderDto.getStatusCode());
        assertEquals(new BigDecimal(100), productsOrderDto.getTotalValue());
        assertEquals(1, productsOrderDto.getTotalAmount());
        assertEquals("Country", productsOrderDto.getShippingAddressDto().getCountry());
        assertEquals("City", productsOrderDto.getShippingAddressDto().getCity());
        assertEquals("PostCode", productsOrderDto.getShippingAddressDto().getPostalCode());
        assertEquals("Name", productsOrderDto.getShippingAddressDto().getName());
        assertEquals("Surname", productsOrderDto.getShippingAddressDto().getSurname());
        assertEquals("Supplier", productsOrderDto.getShippingAddressDto().getSupplier());
        assertEquals("State", productsOrderDto.getShippingAddressDto().getState());
        assertEquals("County", productsOrderDto.getShippingAddressDto().getCounty());
        assertEquals("District", productsOrderDto.getShippingAddressDto().getDistrict());
        assertEquals("Subdistrict", productsOrderDto.getShippingAddressDto().getSubdistrict());
        assertEquals(1, productsOrderDto.getShippingAddressDto().getHouse());
        assertEquals(2, productsOrderDto.getShippingAddressDto().getApartment());
    }

    @Test
    public void buyAllProductInBucketManyItemInBucket() {
        // Given
        ShippingAddressDto shippingAddressDto = new ShippingAddressDto();
        shippingAddressDto.setCode("null");
        shippingAddressDto.setLogin("login");
        Status status = new Status();
        status.setCode("booked");
        when(statusDao.findByCode("booked")).thenReturn(status);
        shippingAddressDto.setSearch("Address");
        AddressDto addressDto = new AddressDto();
        when(locationService.searchLocation(shippingAddressDto.getSearch())).thenReturn(addressDto);
        ShippingAddress shippingAddress = createShippingAddress();
        when(shippingAddressMapper.mapToShippingAddresFromAddressDto(addressDto, shippingAddressDto)).thenReturn(shippingAddress);
        Users user = new Users();
        user.setId(idOne);
        when(userDao.findByLogin(shippingAddressDto.getLogin())).thenReturn(user);

        Bucket bucket = new Bucket();

        Product product = createProduct();
        ProductBucket productBucket = new ProductBucket();
        productBucket.setProduct(product);
        productBucket.setAmount(1);
        bucket.getProductBuckets().add(productBucket);

        Product product2 = createProduct();
        product2.setPrice(new BigDecimal(200));
        ProductBucket productBucket2 = new ProductBucket();
        productBucket2.setProduct(product2);
        productBucket2.setAmount(2);
        bucket.getProductBuckets().add(productBucket2);

        when(bucketDao.findByUser_Id(user.getId())).thenReturn(bucket);
        // When
        ProductsOrderDto productsOrderDto = buyService.buyAllProductInBucket(shippingAddressDto);
        // Then
        assertEquals("booked", productsOrderDto.getStatusCode());
        assertEquals(new BigDecimal(500), productsOrderDto.getTotalValue());
        assertEquals(3, productsOrderDto.getTotalAmount());
        assertEquals("Country", productsOrderDto.getShippingAddressDto().getCountry());
        assertEquals("City", productsOrderDto.getShippingAddressDto().getCity());
        assertEquals("PostCode", productsOrderDto.getShippingAddressDto().getPostalCode());
        assertEquals("Name", productsOrderDto.getShippingAddressDto().getName());
        assertEquals("Surname", productsOrderDto.getShippingAddressDto().getSurname());
        assertEquals("Supplier", productsOrderDto.getShippingAddressDto().getSupplier());
        assertEquals("State", productsOrderDto.getShippingAddressDto().getState());
        assertEquals("County", productsOrderDto.getShippingAddressDto().getCounty());
        assertEquals("District", productsOrderDto.getShippingAddressDto().getDistrict());
        assertEquals("Subdistrict", productsOrderDto.getShippingAddressDto().getSubdistrict());
        assertEquals(1, productsOrderDto.getShippingAddressDto().getHouse());
        assertEquals(2, productsOrderDto.getShippingAddressDto().getApartment());
    }

    @Test
    public void buyAllProductInBucketManyItemInBucketWithCode() {
        // Given
        ShippingAddressDto shippingAddressDto = new ShippingAddressDto();
        shippingAddressDto.setCode("notNull");
        Subscriber subscriber = new Subscriber();
        when(subscriberDao.findByCode(shippingAddressDto.getCode())).thenReturn(subscriber);
        shippingAddressDto.setLogin("login");
        Status status = new Status();
        status.setCode("booked");
        when(statusDao.findByCode("booked")).thenReturn(status);
        shippingAddressDto.setSearch("Address");
        AddressDto addressDto = new AddressDto();
        when(locationService.searchLocation(shippingAddressDto.getSearch())).thenReturn(addressDto);
        ShippingAddress shippingAddress = createShippingAddress();
        when(shippingAddressMapper.mapToShippingAddresFromAddressDto(addressDto, shippingAddressDto)).thenReturn(shippingAddress);
        Users user = new Users();
        user.setId(idOne);
        when(userDao.findByLogin(shippingAddressDto.getLogin())).thenReturn(user);

        Bucket bucket = new Bucket();

        Product product = createProduct();
        ProductBucket productBucket = new ProductBucket();
        productBucket.setProduct(product);
        productBucket.setAmount(1);
        bucket.getProductBuckets().add(productBucket);

        Product product2 = createProduct();
        product2.setPrice(new BigDecimal(200));
        ProductBucket productBucket2 = new ProductBucket();
        productBucket2.setProduct(product2);
        productBucket2.setAmount(2);
        bucket.getProductBuckets().add(productBucket2);

        when(bucketDao.findByUser_Id(user.getId())).thenReturn(bucket);
        // When
        ProductsOrderDto productsOrderDto = buyService.buyAllProductInBucket(shippingAddressDto);
        // Then
        assertEquals("booked", productsOrderDto.getStatusCode());
        assertEquals(new BigDecimal("450.0"), productsOrderDto.getTotalValue());
        assertEquals(3, productsOrderDto.getTotalAmount());
    }

    @Test
    public void cleaningBucket() {
        // Given
        Bucket bucket = new Bucket();

        Product product = createProduct();
        ProductBucket productBucket = new ProductBucket();
        productBucket.setProduct(product);
        productBucket.setAmount(1);
        bucket.getProductBuckets().add(productBucket);

        Product product2 = createProduct();
        product2.setPrice(new BigDecimal(200));
        ProductBucket productBucket2 = new ProductBucket();
        productBucket2.setProduct(product2);
        productBucket2.setAmount(2);
        bucket.getProductBuckets().add(productBucket2);

        List<ProductBucket> productBuckets = new ArrayList<>();

        ProductBucket productBucket3 = new ProductBucket();
        Product product3 = createProduct();
        product3.getProductBuckets().add(productBucket3);
        productBucket3.setProduct(product3);
        productBucket3.setAmount(3);
        productBuckets.add(productBucket3);

        ProductBucket productBucket4 = new ProductBucket();
        Product product4 = createProduct();
        product4.getProductBuckets().add(productBucket4);
        productBucket4.setProduct(product4);
        productBucket4.setAmount(3);
        productBuckets.add(productBucket4);
        // When
        buyService.cleaningBucket(productBuckets, bucket);
        // Then
        assertEquals(0, bucket.getProductBuckets().size());
        assertEquals(0, productBuckets.get(0).getProduct().getProductBuckets().size());
    }

    @Test
    public void cleaningBucketEmpty() {
        // Given
        Bucket bucket = new Bucket();
        List<ProductBucket> productBuckets = new ArrayList<>();
        // When
        buyService.cleaningBucket(productBuckets, bucket);
        // Then
        assertEquals(0, bucket.getProductBuckets().size());
        assertEquals(0, productBuckets.size());
    }

    @Test
    public void getAllProductOrdersUser() {
        // Given
        Users user = new Users();
        user.setId(idOne);
        user.setLogin("login");
        when(userDao.findByLogin("login")).thenReturn(user);
        List<ProductsOrder> productsOrders = new ArrayList<>();
        ProductsOrder productsOrder = createProductsOrder();
        productsOrders.add(productsOrder);
        when(productsOrderDao.findByUser_Id(user.getId())).thenReturn(productsOrders);
        // When
        List<ProductsOrderDto> retrieved = buyService.getAllProductsOrdersUser("login");
        //Then
        assertEquals(1, retrieved.size());
        assertEquals(new BigDecimal("100"), retrieved.get(0).getTotalValue());
        assertEquals(1, retrieved.get(0).getTotalAmount());
        assertEquals("send", retrieved.get(0).getStatusCode());
        assertEquals("Order is send", retrieved.get(0).getStatus());
        assertEquals("login", retrieved.get(0).getUserLogin());
        assertEquals(1, retrieved.get(0).getProductBoughts().size());
        assertEquals("Country", retrieved.get(0).getShippingAddressDto().getCountry());
        assertEquals("linkDelivery", retrieved.get(0).getLinkDelivery());
        assertNotNull(retrieved.get(0).getBoughtDate());
        assertNotNull(retrieved.get(0).getSendDate());
        assertNotNull(retrieved.get(0).getDeliveryDate());
    }

    @Test
    public void getAllProductsOrdersUserEmptyList() {
        Users user = new Users();
        user.setId(idOne);
        user.setLogin("login");
        when(userDao.findByLogin("login")).thenReturn(user);
        List<ProductsOrder> productsOrders = new ArrayList<>();
        // When
        List<ProductsOrderDto> retrieved = buyService.getAllProductsOrdersUser("login");
        // Then
        assertEquals(0, retrieved.size());
    }

    @Test
    public void getProductOrder() {
        // Given
        ProductsOrder productsOrder = createProductsOrder();
        when(productsOrderDao.findOne(idOne)).thenReturn(productsOrder);
        // When
        ProductsOrderDto retrieved = buyService.getProductsOrder(idOne);
        // Then
        assertEquals(new BigDecimal("100"), retrieved.getTotalValue());
        assertEquals(1, retrieved.getTotalAmount());
        assertEquals("send", retrieved.getStatusCode());
        assertEquals("Order is send", retrieved.getStatus());
        assertEquals("login", retrieved.getUserLogin());
        assertEquals(1, retrieved.getProductBoughts().size());
        assertEquals("Country", retrieved.getShippingAddressDto().getCountry());
        assertEquals("linkDelivery", retrieved.getLinkDelivery());
        assertNotNull(retrieved.getBoughtDate());
        assertNotNull(retrieved.getSendDate());
        assertNotNull(retrieved.getDeliveryDate());
    }

    @Test
    public void getProductOrderNotExist() {
        // Given
        when(productsOrderDao.findOne(idOne)).thenReturn(null);
        // When
        ProductsOrderDto retrieved = buyService.getProductsOrder(idOne);
        // Then
        assertEquals(null, retrieved);
    }

    @Test
    public void getAllOrders() {
        // Given
        List<ProductsOrder> productsOrders = new ArrayList<>();
        productsOrders.add(createProductsOrder());
        productsOrders.add(createProductsOrder());
        productsOrders.add(createProductsOrder());
        when(productsOrderDao.findAll()).thenReturn(productsOrders);
        // When
        List<ProductsOrderDto> retrieved = buyService.getAllOrders();
        // Then
        assertEquals(3, retrieved.size());
        assertEquals(new BigDecimal("100"), retrieved.get(0).getTotalValue());
        assertEquals(1, retrieved.get(0).getTotalAmount());
        assertEquals("send", retrieved.get(0).getStatusCode());
        assertEquals("Order is send", retrieved.get(0).getStatus());
        assertEquals("login", retrieved.get(0).getUserLogin());
        assertEquals(1, retrieved.get(0).getProductBoughts().size());
        assertEquals("Country", retrieved.get(0).getShippingAddressDto().getCountry());
        assertEquals("linkDelivery", retrieved.get(0).getLinkDelivery());
        assertNotNull(retrieved.get(0).getBoughtDate());
        assertNotNull(retrieved.get(0).getSendDate());
        assertNotNull(retrieved.get(0).getDeliveryDate());
    }

    @Test
    public void getAllOrdersEmptyList() {
        // Given
        List<ProductsOrder> productsOrders = new ArrayList<>();
        when(productsOrderDao.findAll()).thenReturn(productsOrders);
        // When
        List<ProductsOrderDto> retrieved = buyService.getAllOrders();
        // Then
        assertEquals(0, retrieved.size());
    }

    @Test
    public void paymentVerificationFalse() {
        // Given
        Status status = new Status();
        when(statusDao.findByCode("paid")).thenReturn(status);

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setStatus("NotPaid");
        orderStatus.setOrderId(idOne);

        ProductsOrder productsOrder = new ProductsOrder();
        Status status1 = new Status();
        status1.setCode("booked");
        productsOrder.setStatus(status1);
        when(productsOrderDao.findOne(idOne)).thenReturn(productsOrder);
        // When
        boolean isPaid = buyService.paymentVerification(orderStatus);
        assertEquals(false, isPaid);
    }

    @Test
    public void paymentVerificationTrue() {
        // Given
        Status status = new Status();
        status.setCode("paid");
        when(statusDao.findByCode("paid")).thenReturn(status);

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setStatus("paid");
        orderStatus.setOrderId(idOne);

        ProductsOrder productsOrder = new ProductsOrder();
        Status status1 = new Status();
        status1.setCode("booked");
        productsOrder.setStatus(status1);
        when(productsOrderDao.findOne(idOne)).thenReturn(productsOrder);

        // When
        boolean isPaid = buyService.paymentVerification(orderStatus);
        // Then
        assertEquals("paid", productsOrder.getStatus().getCode());
        assertEquals(true, isPaid);
    }


    @Test
    public void preparedVerificationFalse() {
        // Given
        Status status = new Status();
        when(statusDao.findByCode("prepared")).thenReturn(status);

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setStatus("NotPrepared");
        orderStatus.setOrderId(idOne);

        ProductsOrder productsOrder = new ProductsOrder();
        Status status1 = new Status();
        status1.setCode("NotPrepared");
        productsOrder.setStatus(status1);
        when(productsOrderDao.findOne(idOne)).thenReturn(productsOrder);
        // When
        boolean isPepared = buyService.orderPrepared(orderStatus);
        assertFalse(isPepared);
    }

    @Test
    public void preparedVerificationTrue() {
        // Given
        Status status = new Status();
        status.setCode("prepared");
        when(statusDao.findByCode("prepared")).thenReturn(status);

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(idOne);
        orderStatus.setStatus("prepared");

        ProductsOrder productsOrder = new ProductsOrder();
        Status status1 = new Status();
        status1.setCode("prepared");
        productsOrder.setStatus(status1);
        when(productsOrderDao.findOne(idOne)).thenReturn(productsOrder);
        // When
        boolean isPrepared = buyService.orderPrepared(orderStatus);
        // Then
        assertTrue(isPrepared);
    }


    @Test
    public void orderDeliveredTest() {
        // Given
        Status statusSend = new Status();
        statusSend.setCode("send");
        statusSend.setId(idOne);
        when(statusDao.findByCode("send")).thenReturn(statusSend);

        Status statusDeliverd = new Status();
        statusDeliverd.setCode("delivered");
        when(statusDao.findByCode("delivered")).thenReturn(statusDeliverd);

        List<ProductsOrder> productsOrders = new ArrayList<>();
        ProductsOrder productsOrder = new ProductsOrder();
        productsOrders.add(productsOrder);
        productsOrders.add(productsOrder);
        productsOrders.add(productsOrder);
        when(productsOrderDao.findByStatusId(idOne)).thenReturn(productsOrders);
        // When
        buyService.orderDeliver();
        // Then
        assertEquals(3, productsOrders.size());
        assertEquals("delivered", productsOrders.get(0).getStatus().getCode());
        assertNotNull(productsOrders.get(0).getDeliveredDate());
    }

    @Test
    public void orderDeliverEmptyListTest() {
        // Given
        Status statusSend = new Status();
        statusSend.setCode("send");
        statusSend.setId(idOne);
        when(statusDao.findByCode("send")).thenReturn(statusSend);

        Status statusDeliverd = new Status();
        statusDeliverd.setCode("delivered");
        when(statusDao.findByCode("delivered")).thenReturn(statusDeliverd);

        List<ProductsOrder> productsOrders = new ArrayList<>();
        when(productsOrderDao.findByStatusId(idOne)).thenReturn(productsOrders);
        // When
        buyService.orderDeliver();
        // Then
        assertEquals(0, productsOrders.size());
    }

    @Test
    public void searchOrderContainsProductWithTitle() {
        // Given
        List<Product> productList = new ArrayList<>();
        productList.add(createProduct());
        when(productDao.findProductContainstTitleWithLetters("title")).thenReturn(productList);

        List<ProductBought> productBoughts = new ArrayList<>();
        ProductBought productBought1 = new ProductBought();
        productBought1.setProductsOrder(createProductsOrder());
        productBoughts.add(productBought1);

        ProductBought productBought2 = new ProductBought();
        productBought2.setProductsOrder(createProductsOrder());
        productBoughts.add(productBought2);

        ProductBought productBought3 = new ProductBought();
        productBought3.setProductsOrder(createProductsOrder());
        productBoughts.add(productBought3);
        when(productBoughtDao.findByProductId(idOne)).thenReturn(productBoughts);

        // When
        Set<ProductsOrderDto> orderDtos = buyService.searchOrderContainsProduct("title");
        List<ProductsOrderDto> dtoList = new ArrayList<>(orderDtos);
        // Assert
        assertEquals(3, orderDtos.size());
        assertEquals(new BigDecimal(100), dtoList.get(0).getTotalValue());
        assertEquals(1, dtoList.get(0).getTotalAmount());
    }

    @Test
    public void searchOrderContainsProductWithTilteEmpty() {
        // Given
        List<Product> productList = new ArrayList<>();
        productList.add(createProduct());
        when(productDao.findProductContainstTitleWithLetters("title")).thenReturn(productList);

        List<ProductBought> productBoughts = new ArrayList<>();
        when(productBoughtDao.findByProductId(idOne)).thenReturn(productBoughts);

        // When
        Set<ProductsOrderDto> orderDtos = buyService.searchOrderContainsProduct("title");
        // Assert
        assertEquals(0, orderDtos.size());
    }

    @Test
    public void filterOrderStateAllTest() {
        // Given & When
        List<ProductsOrderDto> dtoList = buyService.filterOrderState("all");
        // Then
        assertEquals(0, dtoList.size());
    }

    @Test
    public void filterOrderStateNullTest() {
        // Given & When
        List<ProductsOrderDto> dtoList = buyService.filterOrderState("null");
        // Then
        assertEquals(0, dtoList.size());
    }

    @Test
    public void filterOrderStateExistTest() {
        // Given
        Status status = new Status();
        status.setId(idOne);
        when(statusDao.findByCode("state")).thenReturn(status);
        List<ProductsOrder> productsOrders = new ArrayList<>();
        productsOrders.add(createProductsOrder());
        when(productsOrderDao.findByStatusId(idOne)).thenReturn(productsOrders);
        List<ProductsOrderDto> productsOrderDtos = buyService.filterOrderState("state");

        assertEquals(1, productsOrderDtos.size());
        assertEquals(new BigDecimal(100), productsOrderDtos.get(0).getTotalValue());
        assertEquals(1, productsOrderDtos.get(0).getTotalAmount());
    }

    @Test
    public void filterOrderDateTest() throws ParseException {
        // Given
        List<ProductsOrder> orderList = new ArrayList<>();
        orderList.add(createProductsOrder());
        orderList.add(createProductsOrder());
        orderList.add(createProductsOrder());

        String inputA = "1.06.2018 00:00:00";
        String inputB = "15.06.2018 23:59:59";
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date dateA = dateFormat.parse(inputA);
        Date dateB = dateFormat.parse(inputB);
        when(productsOrderDao.findByBoughtDateBetween(dateA, dateB)).thenReturn(orderList);
        // When
        List<ProductsOrderDto> retrieved = buyService.filterOrderDate("1.06.2018", "15.06.2018");
        // Then
        assertEquals(3, retrieved.size());
        assertEquals(new BigDecimal(100), retrieved.get(0).getTotalValue());
        assertEquals(1, retrieved.get(0).getTotalAmount());
    }

    @Test
    public void filterOrderDateEmptyList() throws ParseException {
        // Given
        List<ProductsOrder> productsOrders = new ArrayList<>();
        String inputA = "1.06.2018 00:00:00";
        String inputB = "15.06.2018 23:59:59";
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date dateA = dateFormat.parse(inputA);
        Date dateB = dateFormat.parse(inputB);
        when(productsOrderDao.findByBoughtDateBetween(dateA, dateB)).thenReturn(productsOrders);
        // When
        List<ProductsOrderDto> retrieved = buyService.filterOrderDate("1.06.2018", "15.06.2018");
        // Then
        assertEquals(0, retrieved.size());
    }

    @Test
    public void searchOrdersWithIitleTest() {
        List<Product> productList = new ArrayList<>();
        productList.add(createProduct());
        when(productDao.findProductContainstTitleWithLetters("title")).thenReturn(productList);

        List<ProductBought> productBoughts = new ArrayList<>();
        ProductBought productBought1 = new ProductBought();
        productBought1.setProductsOrder(createProductsOrder());
        productBoughts.add(productBought1);

        ProductBought productBought2 = new ProductBought();
        productBought2.setProductsOrder(createProductsOrder());
        productBoughts.add(productBought2);

        ProductBought productBought3 = new ProductBought();
        productBought3.setProductsOrder(createProductsOrder());
        productBoughts.add(productBought3);
        when(productBoughtDao.findByProductId(idOne)).thenReturn(productBoughts);

        OrderSearch orderSearch = new OrderSearch("title", "", "", "", "");
        List<ProductsOrderDto> retrieved = buyService.searchOrders(orderSearch);
        assertEquals(3, retrieved.size());
    }

    @Test
    public void searchOrdersWithDate() throws ParseException {
        // Given
        List<ProductsOrder> orderList = new ArrayList<>();
        orderList.add(createProductsOrder());
        orderList.add(createProductsOrder());
        orderList.add(createProductsOrder());

        String inputA = "1.06.2018 00:00:00";
        String inputB = "15.06.2018 23:59:59";
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date dateA = dateFormat.parse(inputA);
        Date dateB = dateFormat.parse(inputB);
        when(productsOrderDao.findByBoughtDateBetween(dateA, dateB)).thenReturn(orderList);
        OrderSearch orderSearch = new OrderSearch("","1.06.2018", "15.06.2018", "", "");
        // When
        List<ProductsOrderDto> retrieved = buyService.searchOrders(orderSearch);
        // Then
        assertEquals(3, retrieved.size());
    }

    @Test
    public void searchOrdersWithState() {
        // Given
        Status status = new Status();
        status.setId(idOne);
        when(statusDao.findByCode("state")).thenReturn(status);
        List<ProductsOrder> productsOrders = new ArrayList<>();
        productsOrders.add(createProductsOrder());
        when(productsOrderDao.findByStatusId(idOne)).thenReturn(productsOrders);
        OrderSearch orderSearch = new OrderSearch("","", "", "state", "");
        // When
        List<ProductsOrderDto> retrieved = buyService.searchOrders(orderSearch);
        // Then
        assertEquals(1, retrieved.size());
    }

    @Test
    public void searchOrdersWithLogin() {
        // Given
        Users user = new Users();
        user.setId(idOne);
        user.setLogin("login12");
        when(userDao.findByLogin("login12")).thenReturn(user);
        List<ProductsOrder> productsOrders = new ArrayList<>();
        ProductsOrder productsOrder = createProductsOrder();
        productsOrders.add(productsOrder);
        when(productsOrderDao.findByUser_Id(user.getId())).thenReturn(productsOrders);

        OrderSearch orderSearch = new OrderSearch("","", "", "", "login12");
        // When
        List<ProductsOrderDto> retrieved = buyService.searchOrders(orderSearch);
        // Then
        assertEquals(1, retrieved.size());
    }


    @Test
    public void searchOrderWtithTitleDateStateAndLoginZeroCommonOrder() throws ParseException {
        // Given
        // Title
        List<Product> productList = new ArrayList<>();
        productList.add(createProduct());
        when(productDao.findProductContainstTitleWithLetters("title")).thenReturn(productList);

        List<ProductBought> productBoughts = new ArrayList<>();
        ProductBought productBought1 = new ProductBought();
        ProductsOrder productsOrder = createProductsOrder();
        productsOrder.setId((long)1);
        productBought1.setProductsOrder(productsOrder);
        productBoughts.add(productBought1);
        when(productBoughtDao.findByProductId(idOne)).thenReturn(productBoughts);
        // Date
        List<ProductsOrder> orderList = new ArrayList<>();
        ProductsOrder productsOrder2 = createProductsOrder();
        productsOrder2.setId((long)2);
        orderList.add(productsOrder2);

        String inputA = "1.06.2018 00:00:00";
        String inputB = "15.06.2018 23:59:59";
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date dateA = dateFormat.parse(inputA);
        Date dateB = dateFormat.parse(inputB);
        when(productsOrderDao.findByBoughtDateBetween(dateA, dateB)).thenReturn(orderList);

        // State
        Status status = new Status();
        status.setId(idOne);
        when(statusDao.findByCode("state")).thenReturn(status);
        List<ProductsOrder> productsOrders = new ArrayList<>();
        ProductsOrder productsOrder3 = createProductsOrder();
        productsOrder3.setId((long)3);
        productsOrders.add(productsOrder3);
        when(productsOrderDao.findByStatusId(idOne)).thenReturn(productsOrders);
        // Login
        Users user = new Users();
        user.setId(idOne);
        user.setLogin("login12");
        when(userDao.findByLogin("login12")).thenReturn(user);
        List<ProductsOrder> productsOrders2 = new ArrayList<>();
        ProductsOrder productsOrder4 = createProductsOrder();
        productsOrder4.setId((long)4);
        productsOrders.add(productsOrder4);
        when(productsOrderDao.findByUser_Id(user.getId())).thenReturn(productsOrders2);
        OrderSearch orderSearch = new OrderSearch("title","1.06.2018", "15.06.2018", "state", "login12");
        List<ProductsOrderDto> retrieved = buyService.searchOrders(orderSearch);
        assertEquals(0, retrieved.size());
    }

    @Test
    public void searchOrderWtithTitleDateStateAndLogin() throws ParseException {
        // Given
        // Title
        List<Product> productList = new ArrayList<>();
        productList.add(createProduct());
        when(productDao.findProductContainstTitleWithLetters("title")).thenReturn(productList);

        List<ProductBought> productBoughts = new ArrayList<>();
        ProductBought productBought1 = new ProductBought();
        ProductsOrder productsOrder = createProductsOrder();
        productsOrder.setId((long)1);
        productBought1.setProductsOrder(productsOrder);
        productBoughts.add(productBought1);
        when(productBoughtDao.findByProductId(idOne)).thenReturn(productBoughts);
        // Date
        List<ProductsOrder> orderList = new ArrayList<>();
        orderList.add(productsOrder);

        String inputA = "1.06.2018 00:00:00";
        String inputB = "15.06.2018 23:59:59";
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date dateA = dateFormat.parse(inputA);
        Date dateB = dateFormat.parse(inputB);
        when(productsOrderDao.findByBoughtDateBetween(dateA, dateB)).thenReturn(orderList);
        // State
        Status status = new Status();
        status.setId(idOne);
        when(statusDao.findByCode("state")).thenReturn(status);
        List<ProductsOrder> productsOrders = new ArrayList<>();
        productsOrders.add(productsOrder);
        when(productsOrderDao.findByStatusId(idOne)).thenReturn(productsOrders);
        // Login
        Users user = new Users();
        user.setId(idOne);
        user.setLogin("login12");
        when(userDao.findByLogin("login12")).thenReturn(user);
        List<ProductsOrder> productsOrders2 = new ArrayList<>();
        productsOrders2.add(productsOrder);
        when(productsOrderDao.findByUser_Id(user.getId())).thenReturn(productsOrders2);
        OrderSearch orderSearch = new OrderSearch("title","1.06.2018", "15.06.2018", "state", "login12");
        List<ProductsOrderDto> retrieved = buyService.searchOrders(orderSearch);
        assertEquals(1, retrieved.size());
    }

    public ProductsOrder createProductsOrder() {
        Users user = new Users();
        user.setId(idOne);
        user.setLogin("login");
        ProductsOrder productsOrder = new ProductsOrder();
        productsOrder.setTotalValue(new BigDecimal("100"));
        productsOrder.setTotalAmount(1);
        Date boughtDate = new Date();
        productsOrder.setBoughtDate(boughtDate);
        productsOrder.setLinkDelivery("linkDelivery");
        Date sendDate = new Date();
        productsOrder.setSendDate(sendDate);
        Date deliveryDate = new Date();
        productsOrder.setDeliveredDate(deliveryDate);
        productsOrder.setUser(user);
        List<ProductBought> productBoughts = new ArrayList<>();
        ProductBought productBought = new ProductBought();
        productBought.setAmount(1);
        Product product = createProduct();
        productBought.setProduct(product);
        productBoughts.add(productBought);
        productsOrder.setProductBoughts(productBoughts);
        productsOrder.setShippingAddress(createShippingAddress());
        Status status = new Status();
        status.setCode("send");
        status.setName("Order is send");
        productsOrder.setStatus(status);
        return productsOrder;
    }

    public ShippingAddress createShippingAddress() {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setCountry("Country");
        shippingAddress.setCity("City");
        shippingAddress.setPostCode("PostCode");
        shippingAddress.setName("Name");
        shippingAddress.setSurname("Surname");
        shippingAddress.setSupplier("Supplier");
        shippingAddress.setState("State");
        shippingAddress.setCounty("County");
        shippingAddress.setDistrict("District");
        shippingAddress.setSubdistrict("Subdistrict");
        shippingAddress.setHouse(1);
        shippingAddress.setApartment(2);
        return shippingAddress;
    }

    public Product createProduct() {
        Product product = new Product();
        product.setId((long) 1);
        product.setPrice(new BigDecimal(100));
        product.setTitle("title");
        product.setDescription("desc");
        product.setImageLink("image");
        product.setTotalAmount(10);
        product.setAvailableAmount(2);
        ProductStatus productStatus = new ProductStatus("sale", "Product is on Sale");
        product.setStatus(productStatus);
        product.setSumMarks(new BigDecimal(100));
        product.setCountMarks(new BigDecimal(10));
        product.setAverageMarks(new BigDecimal(10));
        return product;
    }


}