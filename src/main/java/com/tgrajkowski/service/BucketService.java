package com.tgrajkowski.service;

import com.tgrajkowski.model.bucket.ProductBucketDto;
import com.tgrajkowski.model.bucket.UserBucketDto;
import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.model.dao.ProductAmountDao;
import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.model.dao.UserDao;
import com.tgrajkowski.model.product.Bucket;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.ProductAmount;
import com.tgrajkowski.model.product.ProductMapper;
import com.tgrajkowski.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BucketService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductAmountDao productAmountDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private BucketDao bucketDao;

    ProductMapper mapper = new ProductMapper();

    public void addProductToBucketList(UserBucketDto userBucketDto) {
        List<Long> listId = userBucketDto.getProductIdArray();
        for (Long id : listId) {
            UserBucketDto userBucketDto1 = new UserBucketDto();
            userBucketDto1.setProductId(id);
            userBucketDto1.setLogin(userBucketDto.getLogin());
            addProductToBucket(userBucketDto1);
        }
    }

    public boolean addProductToBucket(UserBucketDto userBucketDto) {
        String login = userBucketDto.getLogin();
        User user = userDao.findByLogin(login);
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());

        long id = userBucketDto.getProductId();
        Product product = productDao.findById(id);
        List<ProductAmount> amountList = product.getProductAmounts();
        Bucket bucket;
        for (ProductAmount productAmount : amountList) {
            bucket = productAmount.getBucket();
            if (bucket == null) {
                bucket = userBucket;
                productAmount.setBucket(bucket);
                productAmountDao.save(productAmount);
                return true;
            }
        }
        return false;
    }

    public boolean removeSinggleItemFromBucket(UserBucketDto userBucketDto) {
        String loging = userBucketDto.getLogin();
        User user = userDao.findByLogin(loging);
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());

        List<ProductAmount> amountList = userBucket.getProductAmount();
        Long productId =userBucketDto.getProductId();

        for (ProductAmount productAmount: amountList) {
            if (productAmount.getProduct().getId()==productId) {
                productAmount.setBucket(null);
                productAmountDao.save(productAmount);
                return true;
            }
        }
        return false;
    }

    public Set<ProductBucketDto> showProductInBucket(String login) {
        User user = userDao.findByLogin(login);
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());
        List<ProductAmount> list = productAmountDao.getDistinctByBucket_Id((userBucket.getId()));

        Set<ProductBucketDto> bucketDtos = new HashSet<>();
        for (ProductAmount productAmount : list) {
            ProductBucketDto bucketDto1 = mapper.maptoProcuctBucketDto(productAmount.getProduct());
            int amount = 0;
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

    public void removeProductFromBucket(String login) {
        User user = userDao.findByLogin(login);
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());
        List<ProductAmount> amountList = userBucket.getProductAmount();
        for (ProductAmount productAmount : amountList) {
            productAmount.setBucket(null);
        }
        userBucket.setProductAmount(amountList);
        bucketDao.save(userBucket);
    }

    public boolean removeSingleProductFromBucket(UserBucketDto userBucketDto){
        User user = userDao.findByLogin(userBucketDto.getLogin());
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());
        List<ProductAmount> amountList = userBucket.getProductAmount();

        for (ProductAmount productAmount : amountList) {
            if (productAmount.getProduct().getId()==userBucketDto.getProductId()){
                productAmount.setBucket(null);
            }
        }
        try {
            userBucket.setProductAmount(amountList);
            bucketDao.save(userBucket);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
