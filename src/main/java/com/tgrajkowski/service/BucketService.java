package com.tgrajkowski.service;

import com.tgrajkowski.model.bucket.UserBucketDto;
import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.model.dao.ProductBucketDao;
import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.model.dao.UserDao;
import com.tgrajkowski.model.product.*;
import com.tgrajkowski.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BucketService {
    @Autowired
    private ProductDao productDao;


    @Autowired
    private UserDao userDao;

    @Autowired
    private BucketDao bucketDao;

    @Autowired
    ProductBucketDao productBucketDao;


    ProductMapper mapper = new ProductMapper();
    ProductBucketMapper productBucketMapper = new ProductBucketMapper();

    public void addProductToBucketList(UserBucketDto userBucketDto) {
        List<Long> longProductIdList = userBucketDto.getProductIdArray();
        for (Long productId: longProductIdList) {
            UserBucketDto userBucketDtoTemp = new UserBucketDto(productId, userBucketDto.getLogin());
            addProductToBucket(userBucketDtoTemp);
        }
    }

    public boolean addProductToBucket(UserBucketDto userBucketDto) {
        String login = userBucketDto.getLogin();
        User user = userDao.findByLogin(login);
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());

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

    public void removeProductFromBucket(String login) {
        User user = userDao.findByLogin(login);
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());

        bucketDao.save(userBucket);
    }

    public void removeSinggleItemFromBucket(UserBucketDto userBucketDto) {
        String loging = userBucketDto.getLogin();
        User user = userDao.findByLogin(loging);
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());
        Product product = productDao.findById(userBucketDto.getProductId());

        ProductBucketPK productBucketPK = new ProductBucketPK(product.getId(), userBucket.getId());
        ProductBucket productBucket = productBucketDao.findOne(productBucketPK);

        if (productBucket.getAmount() > 1) {
            productBucket.setAmount(productBucket.getAmount() - 1);
            productBucketDao.save(productBucket);

            product.setAvailableAmount(product.getAvailableAmount() + 1);
            productDao.save(product);
        } else {
            removeSingleProductFromBucket(userBucketDto);
        }
    }


    public void removeSingleProductFromBucket(UserBucketDto userBucketDto) {
        User user = userDao.findByLogin(userBucketDto.getLogin());
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());
        Product product = productDao.findById(userBucketDto.getProductId());

        ProductBucketPK productBucketPK = new ProductBucketPK(product.getId(), userBucket.getId());
        ProductBucket productBucket = productBucketDao.findOne(productBucketPK);

        userBucket.getProductBuckets().remove(productBucket);
        bucketDao.save(userBucket);

        product.getProductBuckets().remove(productBucket);
        product.setAvailableAmount(product.getTotalAmount());
        productDao.save(product);


        productBucketDao.delete(productBucket);
    }
}
