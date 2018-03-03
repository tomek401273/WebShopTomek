package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProductDao extends CrudRepository<Product, Long> {
    List<Product> findAll();

    @Override
    Product save(Product product);
    void deleteById(Long id);

    void delete(Product product);

    Product findById(Long id);
}
