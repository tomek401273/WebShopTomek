package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.order.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface StatusDao extends CrudRepository<Status, Long>{
    Status findByCode(String code);
}
