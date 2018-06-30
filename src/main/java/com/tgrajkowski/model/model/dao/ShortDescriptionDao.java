package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.ShortDescription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ShortDescriptionDao extends CrudRepository<ShortDescription, Long> {
    List<ShortDescription> findByProductId(Long id);
}
