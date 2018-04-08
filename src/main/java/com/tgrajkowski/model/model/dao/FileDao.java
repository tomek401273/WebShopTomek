package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface FileDao extends CrudRepository<File, Integer>{
    File findByName(String name);
}
