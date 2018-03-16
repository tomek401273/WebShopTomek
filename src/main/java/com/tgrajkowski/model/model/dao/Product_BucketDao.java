package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.Product_Bucket;
import com.tgrajkowski.model.product.Product_BucketPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface Product_BucketDao extends JpaRepository<Product_Bucket, Product_BucketPK> {

}
