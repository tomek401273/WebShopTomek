package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.category.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface CategoryDao extends CrudRepository<Category, Long> {
    Category findByName(String name);
    List<Category> findAll();
}
