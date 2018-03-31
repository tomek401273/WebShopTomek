package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProductDao extends CrudRepository<Product, Long> {
    List<Product> findAll();

    Product findById(Long id);

    List<Product> findByTitle(String title);

    @Override
    Product save(Product product);

    void deleteById(Long id);

    @Query(nativeQuery = true)
    List<Product> findProductContainstTitleWithLetters(@Param("LETTERS") String letters);

    @Query
    List<Product> findProductWithPriceBetween(@Param("ABOVE") int above, @Param("BELOW") int below);

    List<Product> findByStatusId(Long statusId);

}
