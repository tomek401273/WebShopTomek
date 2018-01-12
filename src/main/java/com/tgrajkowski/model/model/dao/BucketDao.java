package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.Bucket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BucketDao extends CrudRepository<Bucket, Long> {
}
