package com.tgrajkowski.service;

import com.tgrajkowski.model.bucket.Bucket;
import com.tgrajkowski.model.bucket.UserBucketDto;
import com.tgrajkowski.model.model.dao.*;
import com.tgrajkowski.model.newsletter.Subscriber;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.ProductStatus;
import com.tgrajkowski.model.product.bucket.ProductBucket;
import com.tgrajkowski.model.product.bucket.ProductBucketDto;
import com.tgrajkowski.model.product.bucket.ProductBucketPK;
import com.tgrajkowski.model.product.comment.Comment;
import com.tgrajkowski.model.user.UserAddress;
import com.tgrajkowski.model.user.UserDto;
import com.tgrajkowski.model.user.Users;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BucketServiceSuiteTest {
    @InjectMocks
    BucketService bucketService;

    @Mock
    private ProductDao productDao;

    @Mock
    private UserDao userDao;

    @Mock
    private BucketDao bucketDao;

    @Mock
    private ProductBucketDao productBucketDao;

    @Mock
    private SubscriberDao subscriberDao;

    private Long idOne = new Long(1);


    @Before
    public void init() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("login");
    }

    @Test
    public void addProductToBucketOneAvaiableItemsTest() {
        // Given
        Users user = new Users();
        user.setId(idOne);
        when(userDao.findByLogin("login")).thenReturn(user);
        Bucket bucket = new Bucket();
        bucket.setId(idOne);
        when(bucketDao.findByUser_Id(idOne)).thenReturn(bucket);
        Product product = new Product();
        product.setId(idOne);
        product.setAvailableAmount(1);
        when(productDao.findById(idOne)).thenReturn(product);

        UserBucketDto userBucketDto = new UserBucketDto();
        userBucketDto.setProductId(idOne);
        // When
        boolean result = bucketService.addProductToBucket(userBucketDto);
        // Then
        Assert.assertTrue(result);
        Assert.assertEquals(0, product.getAvailableAmount());
    }

    @Test
    public void addProductToBuckeZeroAvaiableItemsTest() {
        // Given
        Users user = new Users();
        user.setId(idOne);
        when(userDao.findByLogin("login")).thenReturn(user);
        Bucket bucket = new Bucket();
        bucket.setId(idOne);
        when(bucketDao.findByUser_Id(idOne)).thenReturn(bucket);
        Product product = new Product();
        product.setId(idOne);
        product.setAvailableAmount(0);
        when(productDao.findById(idOne)).thenReturn(product);

        UserBucketDto userBucketDto = new UserBucketDto();
        userBucketDto.setProductId(idOne);
        // When
        boolean result = bucketService.addProductToBucket(userBucketDto);
        // Then
        Assert.assertFalse(result);
        assertEquals(0, product.getAvailableAmount());
    }

    @Test
    public void addProductToBucketListTest() {
        List<Long> productIdArray = new ArrayList<>();
        productIdArray.add((long) 1);
        productIdArray.add((long) 2);
        UserBucketDto userBucketDto = new UserBucketDto();
        userBucketDto.setProductIdArray(productIdArray);
        Users user = new Users();
        user.setId(idOne);
        when(userDao.findByLogin("login")).thenReturn(user);
        Bucket bucket = new Bucket();
        bucket.setId(idOne);
        when(bucketDao.findByUser_Id(idOne)).thenReturn(bucket);

        Product product = new Product();
        product.setId(idOne);
        product.setAvailableAmount(1);
        when(productDao.findById(idOne)).thenReturn(product);

        Product product2 = new Product();
        product2.setId(idOne);
        product2.setAvailableAmount(1);
        when(productDao.findById((long) 2)).thenReturn(product2);
        // When
        bucketService.addProductToBucketList(userBucketDto);
        // Then
        System.out.println("bucket: " + bucket);
        System.out.println("product: " + product);
        assertEquals(0, product.getAvailableAmount());
        assertEquals(0, product2.getAvailableAmount());
    }

    @Test
    public void showProductInBucketTest() {
        // Given
        Users user = new Users();
        user.setId(idOne);
        when(userDao.findByLogin("login")).thenReturn(user);
        Bucket bucket = new Bucket();
        bucket.setId(idOne);

        ProductBucket productBucket = new ProductBucket();
        productBucket.setProduct(createProduct());
        productBucket.setAmount(7);
        productBucket.setBucket(bucket);
        productBucket.setDateAdded(new Date());
        bucket.getProductBuckets().add(productBucket);
        when(bucketDao.findByUser_Id(idOne)).thenReturn(bucket);
        // When
        List<ProductBucketDto> productBucketDtos = bucketService.showProductInBucket();
        assertEquals(1, productBucketDtos.size());
        assertEquals(7, productBucketDtos.get(0).getAmount());
    }

    @Test
    public void showProductInBucketEmptyTest() {
        // Given
        Users user = new Users();
        user.setId(idOne);
        when(userDao.findByLogin("login")).thenReturn(user);
        Bucket bucket = new Bucket();
        bucket.setId(idOne);
        when(bucketDao.findByUser_Id(idOne)).thenReturn(bucket);
        // When
        List<ProductBucketDto> productBucketDtos = bucketService.showProductInBucket();
        assertEquals(0, productBucketDtos.size());
    }

    @Test
    public void removeSingleItemFromBucketTest() {
        // Given
        Users user = new Users();
        user.setId(idOne);
        when(userDao.findByLogin("login")).thenReturn(user);
        Bucket bucket = new Bucket();
        bucket.setId(idOne);
        when(bucketDao.findByUser_Id(idOne)).thenReturn(bucket);
        Product product = new Product();
        product.setId(idOne);
        product.setAvailableAmount(1);
        when(productDao.findById(idOne)).thenReturn(product);

        ProductBucket productBucket = new ProductBucket();
        productBucket.setProduct(createProduct());
        productBucket.setAmount(7);
        productBucket.setBucket(bucket);
        productBucket.setDateAdded(new Date());
        ProductBucketPK productBucketPK = new ProductBucketPK(idOne, idOne);
        when(productBucketDao.findOne(productBucketPK)).thenReturn(productBucket);

        // When
        boolean result = bucketService.removeSinggleItemFromBucket(idOne);
        // Then
        assertTrue(result);
        assertEquals(6, productBucket.getAmount());
    }

    @Test
    public void checkRemovingProcessCorrectlyTest() {
        // Given
        ProductBucketPK productBucketPK = new ProductBucketPK(idOne, idOne);
        ProductBucket productBucket = new ProductBucket();
        productBucket.setProduct(createProduct());
        productBucket.setAmount(7);
        Bucket bucket = new Bucket();
        productBucket.setBucket(bucket);
        productBucket.setDateAdded(new Date());
        when(productBucketDao.findOne(productBucketPK)).thenReturn(productBucket);
        // When
        boolean result = bucketService.checkRemovingProcess(productBucketPK, 8);
        // Then
        assertTrue(result);
    }

    @Test
    public void checkRemovingProcessNotCorrectlyTest() {
        // Given
        ProductBucketPK productBucketPK = new ProductBucketPK(idOne, idOne);
        ProductBucket productBucket = new ProductBucket();
        productBucket.setProduct(createProduct());
        productBucket.setAmount(7);
        Bucket bucket = new Bucket();
        productBucket.setBucket(bucket);
        productBucket.setDateAdded(new Date());
        when(productBucketDao.findOne(productBucketPK)).thenReturn(productBucket);
        // When
        boolean result = bucketService.checkRemovingProcess(productBucketPK, 7);
        // Then
        assertFalse(result);
    }

    @Test
    public void removeSingleProductFromBucket() {
        // Given
        Users user = new Users();
        user.setId(idOne);
        when(userDao.findByLogin("login")).thenReturn(user);

        ProductBucketPK productBucketPK = new ProductBucketPK(idOne, idOne);
        ProductBucket productBucket = new ProductBucket();
        when(productBucketDao.findOne(productBucketPK)).thenReturn(productBucket);

        Bucket bucket = new Bucket();
        bucket.setId(idOne);
        bucket.getProductBuckets().add(productBucket);
        when(bucketDao.findByUser_Id(idOne)).thenReturn(bucket);

        Product product = new Product();
        product.setId(idOne);
        product.setAvailableAmount(1);
        product.setTotalAmount(10);
        product.getProductBuckets().add(productBucket);
        when(productDao.findById(idOne)).thenReturn(product);
        //When
        bucketService.removeSingleProductFromBucket(idOne);
        // Then
        assertEquals(10, product.getAvailableAmount());
        assertEquals(0, bucket.getProductBuckets().size());
        assertEquals(0, product.getProductBuckets().size());
    }

    @Test
    public void removeSingleProductFromBucketProductNotExist() {
        // Given
        Users user = new Users();
        user.setId(idOne);
        when(userDao.findByLogin("login")).thenReturn(user);

        ProductBucketPK productBucketPK = new ProductBucketPK(idOne, idOne);
        ProductBucket productBucket = new ProductBucket();
        when(productBucketDao.findOne(productBucketPK)).thenReturn(productBucket);

        Bucket bucket = new Bucket();
        bucket.setId(idOne);
        bucket.getProductBuckets().add(productBucket);
        when(bucketDao.findByUser_Id(idOne)).thenReturn(bucket);

        Product product = new Product();
        product.setId(idOne);
        product.setAvailableAmount(1);
        product.setTotalAmount(10);
        product.getProductBuckets().add(productBucket);
        when(productDao.findById(idOne)).thenReturn(product);
        //When
        bucketService.removeSingleProductFromBucket((long)2);
        // Then
        assertEquals(1, product.getAvailableAmount());
        assertEquals(1, bucket.getProductBuckets().size());
        assertEquals(1, product.getProductBuckets().size());
    }

    @Test
    public void getAddresShippingTest() {
        // Given
        Users user = new Users();
        user.setId(idOne);
        user.setName("name");
        user.setSurname("surname");
        UserAddress userAddress = new UserAddress();
        userAddress.setCountry("Country");
        userAddress.setCity("City");
        userAddress.setPostCode("postCode");
        userAddress.setStreet("street");
        userAddress.setDistrict("district");
        userAddress.setHouse(1);
        userAddress.setApartment(2);
        user.setUserAddress(userAddress);
        when(userDao.findByLogin("login")).thenReturn(user);
        UserDto retrieved = bucketService.getAddressShipping();
        // Then
        assertEquals("name", retrieved.getName());
        assertEquals("surname", retrieved.getSurname());
        assertEquals("Country", retrieved.getCountry());
        assertEquals("postCode", retrieved.getPostCode());
        assertEquals("street", retrieved.getStreet());
        assertEquals("district", retrieved.getDistrict());
        assertEquals(1, retrieved.getHouse());
        assertEquals(2, retrieved.getApartment());
    }

    @Test
    public void checkCodeAvailableTest() {
        // Given
        Subscriber subscriber = new Subscriber();
        when(subscriberDao.findByCode("code")).thenReturn(subscriber);
        // When
        boolean retrieved = bucketService.checkCodeAvailable("code");
        // Then
        assertTrue(retrieved);
    }

    @Test
    public void checkCodeAvailableFalseTest() {
        // Given
        when(subscriberDao.findByCode("code")).thenReturn(null);
        // Then
        boolean retrieved = bucketService.checkCodeAvailable("code");
        assertFalse(retrieved);
    }



    public Product createProduct() {
        Product product = new Product();
        product.setId((long) 1);
        product.setPrice(new BigDecimal(100));
        product.setTitle("Super Computer");
        product.setDescription("Super Computer");
        product.setImageLink("https://image3.mouthshut.com/images/imagesp/925872676s.jpg");
        product.setTotalAmount(10);
        product.setAvailableAmount(2);
        ProductStatus productStatus = new ProductStatus("sale", "Product is on Sale");
        product.setStatus(productStatus);
        product.setSumMarks(new BigDecimal(100));
        product.setCountMarks(new BigDecimal(10));
        product.setAverageMarks(new BigDecimal(10));

        List<Comment> commentList = new ArrayList<>();
        Comment comment = new Comment();
        comment.setId((long) 1);
        Users users = new Users();
        users.setLogin("test@test.com");
        comment.setUser(users);
        comment.setCreated(new Date());
        comment.setProduct(product);
        commentList.add(comment);
        product.setComments(commentList);
        return product;
    }
}
