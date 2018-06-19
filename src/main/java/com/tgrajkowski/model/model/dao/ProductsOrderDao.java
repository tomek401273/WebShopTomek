package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.order.ProductsOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface ProductsOrderDao extends CrudRepository<ProductsOrder, Long> {
    List<ProductsOrder> findByUser_Id(Long id);
    List<ProductsOrder> findAll();
    List<ProductsOrder> findByStatusId(Long id);
    List<ProductsOrder> findByBoughtDateBetween(Date after, Date before);
}
