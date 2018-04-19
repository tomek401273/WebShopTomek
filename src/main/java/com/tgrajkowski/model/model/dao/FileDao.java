package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.file.File;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface FileDao extends CrudRepository<File, Integer>{
    File findByName(String name);

    @Query(nativeQuery = true)
    boolean savePhotoBase64(@Param("ID") Long id, @Param("BASE64") String base64, @Param("NAME") String name);
}
