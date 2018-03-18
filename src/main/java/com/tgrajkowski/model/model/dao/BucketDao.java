package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.Bucket;
import com.tgrajkowski.model.product.Product;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface BucketDao extends CrudRepository<Bucket, Long> {
    List<Bucket> findAll();
    Bucket findById(Long id);
    Bucket findByUser_Id(Long id);
}
