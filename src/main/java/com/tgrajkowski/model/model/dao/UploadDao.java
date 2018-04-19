package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.Upload;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface UploadDao extends CrudRepository<Upload, Long> {
}
