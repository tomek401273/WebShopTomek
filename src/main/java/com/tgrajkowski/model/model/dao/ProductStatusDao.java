package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.ProductStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ProductStatusDao extends CrudRepository<ProductStatus, Long> {
    ProductStatus findProductStatusByCode(String code);
}
