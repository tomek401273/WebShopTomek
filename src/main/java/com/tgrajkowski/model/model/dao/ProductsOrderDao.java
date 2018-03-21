package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.ProductsOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ProductsOrderDao extends CrudRepository<ProductsOrder, Long> {
}
