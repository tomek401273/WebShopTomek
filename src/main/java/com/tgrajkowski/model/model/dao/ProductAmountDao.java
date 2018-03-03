package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.ProductAmount;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductAmountDao extends CrudRepository<ProductAmount, Long> {
    //    @Query(nativeQuery = true)
//    void saveProductAmount(@Param("PRODUCT_ID") int productId);


    ProductAmount findById(Long id);

    List<ProductAmount> countDistinctByBucket_Id(Long id);

    List<ProductAmount> countByBucket_Id(Long id);

    List<ProductAmount> getDistinctByBucket_Id(Long id);


}
