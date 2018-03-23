package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.bought.ProductBought;
import com.tgrajkowski.model.product.bought.ProductBoughtPK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ProductBoughtDao extends CrudRepository<ProductBought, ProductBoughtPK>{


}