package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.bought.ProductBought;
import com.tgrajkowski.model.product.bought.ProductBoughtPK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProductBoughtDao extends CrudRepository<ProductBought, ProductBoughtPK>{
//    ProductBought findByProductId(Long id);
    List<ProductBought> findByProductId(Long id);
//    ProductBought findByProductTitle(String title);

}