package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.bucket.ProductBucket;
import com.tgrajkowski.model.product.bucket.ProductBucketPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProductBucketDao extends JpaRepository<ProductBucket, ProductBucketPK> {
    @Override
    ProductBucket findOne(ProductBucketPK productBucketPK);
    List<ProductBucket> findByProductId(Long productId);
    List<ProductBucket> findByBucket_Id(Long bucketId);
}
