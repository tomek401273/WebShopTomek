package com.tgrajkowski.service;

import com.tgrajkowski.model.bucket.ProductBucketDto;
import com.tgrajkowski.model.bucket.UserBucketDto;
import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.model.dao.UserDao;
import com.tgrajkowski.model.product.Bucket;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.ProductMapper;
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

        return false;
    }

    public boolean removeSinggleItemFromBucket(UserBucketDto userBucketDto) {
        String loging = userBucketDto.getLogin();
        User user = userDao.findByLogin(loging);
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());

        return false;
    }

    public Set<ProductBucketDto> showProductInBucket(String login) {
        User user = userDao.findByLogin(login);
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());
        return null;
    }

    public void removeProductFromBucket(String login) {
        User user = userDao.findByLogin(login);
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());

        bucketDao.save(userBucket);
    }

    public boolean removeSingleProductFromBucket(UserBucketDto userBucketDto){
        User user = userDao.findByLogin(userBucketDto.getLogin());
        Bucket userBucket = bucketDao.findByUser_Id(user.getId());
       return false;
    }
}
