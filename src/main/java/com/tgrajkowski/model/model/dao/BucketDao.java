package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.Bucket;
import com.tgrajkowski.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface BucketDao extends CrudRepository<Bucket, Long> {
    List<Bucket> findAll();
}
