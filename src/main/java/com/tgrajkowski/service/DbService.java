package com.tgrajkowski.service;

import com.tgrajkowski.model.bucket.ProductBucketDto;
import com.tgrajkowski.model.bucket.UserBucketDto;
import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.model.dao.ProductAmountDao;
import com.tgrajkowski.model.model.dao.UserDao;
import com.tgrajkowski.model.product.*;
import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.user.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.boot.jaxb.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import org.hibernate.Criteria;
import org.hibernate.Session;

@Service
public class DbService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductAmountDao productAmountDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BucketDao bucketDao;

    ProductMapper mapper = new ProductMapper();

    public Product saveProduct(ProductDto productDto) {

        Product product = mapper.mapToProduct(productDto);
        int amount = productDto.getAmount();

        for (int i = 0; i < amount; i++) {
            ProductAmount productAmount = new ProductAmount();
            productAmount.setProduct(product);
            product.getProductAmounts().add(productAmount);
        }
        return productDao.save(product);
    }

    public int checkAvailable(Long id) {
        Product product = productDao.findById(id);
        ProductDto productDto = mapper.mapToProductDto(product);
        int allProducts = productDto.getAmount();
        int bookedProducts = productDto.getBookedProduct();
        int availableProducts = allProducts - bookedProducts;
        System.out.println("allProducts: " + allProducts);
        System.out.println("bookedProducts: " + bookedProducts);
        System.out.println("available: " + availableProducts);
        return availableProducts;
    }

    public boolean addProductToBucket(UserBucketDto userBucketDto) {
        boolean success = false;
        System.out.println("start");
        String login = userBucketDto.getLogin();
        User user = userDao.findByLogin(login);
        System.out.println("User Login:" + user.getLogin());
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());
        System.out.println("Bucket Id: " + userBucket.getId());


        long id = userBucketDto.getId();
        Product product = productDao.findById(id);
        List<ProductAmount> amountList = product.getProductAmounts();
        Bucket bucket;
        for (ProductAmount productAmount : amountList) {
            bucket = productAmount.getBucket();
            System.out.println("productAmountID" + productAmount.getId());
            if (bucket == null) {
                System.out.println("bucket == null");
                bucket = userBucket;
                productAmount.setBucket(bucket);
                productAmountDao.save(productAmount);
                success = true;
                break;
            } else {
                System.out.println("In our database not exist free products");
            }
            System.out.println("iteration end");
        }
        System.out.println("end");
        return success;
    }

    public void addProductToBucketList(UserBucketDto userBucketDto) {
        List<Long> listId = userBucketDto.getListId();
        for (Long id : listId) {
            UserBucketDto userBucketDto1 = new UserBucketDto();
            userBucketDto1.setId(id);
            userBucketDto1.setLogin(userBucketDto.getLogin());
            addProductToBucket(userBucketDto1);
        }
    }


    public void removeProductFromBucket(String login) {
        System.out.println("start");

        User user = userDao.findByLogin(login);
        System.out.println("User Login:" + user.getLogin());
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());
        List<ProductAmount> amountList = userBucket.getProductAmount();

        for (ProductAmount productAmount : amountList) {
            productAmount.setBucket(null);
        }
        userBucket.setProductAmount(amountList);
        bucketDao.save(userBucket);
        System.out.println("end");
    }

    public Set<ProductBucketDto> showProductInBucket(String login) {
        List<ProductAmount> list = productAmountDao.getDistinctByBucket_Id((long) 3);
        Set<ProductBucketDto> bucketDtos = new HashSet<>();
        for (ProductAmount productAmount : list) {
            ProductBucketDto bucketDto1 = mapper.maptoProcuctBucketDto(productAmount.getProduct());
            int amount =0;
            for (ProductAmount productAmount2 : list) {
                ProductBucketDto bucketDto2 = mapper.maptoProcuctBucketDto(productAmount2.getProduct());
                if (bucketDto1.equals(bucketDto2)) {
                    amount++;
                }
            }
            bucketDto1.setAmount(amount);
            bucketDtos.add(bucketDto1);
        }
        return bucketDtos;
    }


    public List<ProductDto> getProducts() {
        List<Product> products = productDao.findAll();
        return mapper.mapToProductDtoList(products);
    }


    public void removeProductFromDaatabase(Long productId) {
        productDao.deleteById(productId);
    }

}
