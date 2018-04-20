package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.file.Photo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import javax.websocket.Decoder;

@Transactional
@Repository
public interface FileDao extends CrudRepository<Photo, Integer>{
    Photo findByName(String name);

    @Query(nativeQuery = true)
    boolean savePhotoBase64(@Param("ID") Long id, @Param("BYTEA") byte[] bytes, @Param("NAME") String name);
}
