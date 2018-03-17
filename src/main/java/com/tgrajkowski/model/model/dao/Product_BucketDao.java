package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.ProductBucket;
import com.tgrajkowski.model.product.ProductBucketPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface Product_BucketDao extends JpaRepository<ProductBucket, ProductBucketPK> {

}
