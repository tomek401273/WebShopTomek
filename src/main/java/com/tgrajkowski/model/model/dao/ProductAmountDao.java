package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.ProductAmount;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductAmountDao extends CrudRepository<ProductAmount, Long> {

//    @Query(nativeQuery = true)
//    void deleteD();

    void deleteAllByProductId(Long id);
    ProductAmount findById(Long id);

    List<ProductAmount> countDistinctByBucket_Id(Long id);

    List<ProductAmount> countByBucket_Id(Long id);

    List<ProductAmount> getDistinctByBucket_Id(Long id);
    List<ProductAmount> findByProductId(Long id);
//    boolean deleteById(Long id);


//    void deleteByProduct_Id(Long id);
//
//    void deleteById(Long id);

}
