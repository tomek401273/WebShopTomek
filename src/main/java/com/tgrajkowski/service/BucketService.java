package com.tgrajkowski.service;

import com.tgrajkowski.model.bucket.Bucket;
import com.tgrajkowski.model.bucket.UserBucketDto;
import com.tgrajkowski.model.model.dao.*;
import com.tgrajkowski.model.product.*;
import com.tgrajkowski.model.product.bucket.ProductBucket;
import com.tgrajkowski.model.product.bucket.ProductBucketDto;
import com.tgrajkowski.model.product.bucket.ProductBucketMapper;
import com.tgrajkowski.model.product.bucket.ProductBucketPK;
import com.tgrajkowski.model.user.User;
import com.tgrajkowski.model.user.UserDto;
import com.tgrajkowski.model.user.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BucketService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BucketDao bucketDao;

    @Autowired
    private ProductBucketDao productBucketDao;

    @Autowired
    private SubscriberDao subscriberDao;

    private ProductBucketMapper productBucketMapper = new ProductBucketMapper();
    private UserMapper userMapper = new UserMapper();


    public void addProductToBucketList(UserBucketDto userBucketDto) {
        List<Long> longProductIdList = userBucketDto.getProductIdArray();
        for (Long productId : longProductIdList) {
            UserBucketDto userBucketDtoTemp = new UserBucketDto(productId, userBucketDto.getLogin());
            addProductToBucket(userBucketDtoTemp);
        }
    }

    public boolean addProductToBucket(UserBucketDto userBucketDto) {
        String login = userBucketDto.getLogin();
        User user = userDao.findByLogin(login);
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());
        log.info("user: " + login + " add Product to Bucket");
// programowanie aspektowe logownaie informacji
        long id = userBucketDto.getProductId();
        Product product = productDao.findById(id);
        if (product.getAvailableAmount() > 0) {
            ProductBucketPK productBucketPK = new ProductBucketPK(product.getId(), userBucket.getId());

            ProductBucket productBucket = productBucketDao.findOne(productBucketPK);
            int amountBucketedProduct = 0;

            if (productBucket != null) {

                amountBucketedProduct = productBucket.getAmount();
            }

            product.setAvailableAmount(product.getAvailableAmount() - 1);

            ProductBucket productBucket2 = new ProductBucket(product, userBucket, amountBucketedProduct + 1);
            productBucketDao.save(productBucket2);
            return true;
        }
        return false;
    }


    public List<ProductBucketDto> showProductInBucket(String login) {
        User user = userDao.findByLogin(login);
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());
        List<ProductBucket> productBuckets = userBucket.getProductBuckets();

        List<ProductBucketDto> productBucketDtoList = productBucketMapper.mapToProductBucketDtoList(productBuckets);
        return productBucketDtoList;
    }

    public boolean removeSinggleItemFromBucket(String login, Long productId) {
        int productBucketAmountActual;
        User user = userDao.findByLogin(login);
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());
        Product product = productDao.findById(productId);

        ProductBucketPK productBucketPK = new ProductBucketPK(product.getId(), userBucket.getId());
        ProductBucket productBucket = productBucketDao.findOne(productBucketPK);

        productBucketAmountActual = productBucket.getAmount();
        if (productBucketAmountActual > 1) {
            productBucket.setAmount(productBucket.getAmount() - 1);
            productBucketDao.save(productBucket);

            product.setAvailableAmount(product.getAvailableAmount() + 1);
            productDao.save(product);
        } else {
            removeSingleProductFromBucket(login, productId);
        }
        return checkRemovingProdces(productBucketPK, productBucketAmountActual);


    }

    public boolean checkRemovingProdces(ProductBucketPK productBucketPK, int productBucketAmountActual) {
        Optional<ProductBucket> optionalProductBucket = Optional.ofNullable(productBucketDao.findOne(productBucketPK));
//        optionalProductBucket.isPresent();
//        if (optionalProductBucket.isPresent()) {
//            if (optionalProductBucket.get().getAmount() < productBucketAmountActual) {
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return true;
//        }

      return optionalProductBucket.filter(x->x.getAmount()< productBucketAmountActual).isPresent();
    }

    public void removeSingleProductFromBucket(String login, Long productId) {
        User user = userDao.findByLogin(login);
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());
        Product product = productDao.findById(productId);

        ProductBucketPK productBucketPK = new ProductBucketPK(product.getId(), userBucket.getId());
        ProductBucket productBucket = productBucketDao.findOne(productBucketPK);

        userBucket.getProductBuckets().remove(productBucket);
        bucketDao.save(userBucket);

        product.getProductBuckets().remove(productBucket);
        product.setAvailableAmount(product.getTotalAmount());
        productDao.save(product);
        productBucketDao.delete(productBucket);
    }

    public UserDto getAddressShipping(String login) {
        User user = userDao.findByLogin(login);
        UserDto userDto = userMapper.mapToUserDto(user);
        return userDto;
    }

    public boolean checkCodeAvailable(String code) {
       if(subscriberDao.findByCode(code) != null) {
           return true;
       }
       return false;
    }
}
