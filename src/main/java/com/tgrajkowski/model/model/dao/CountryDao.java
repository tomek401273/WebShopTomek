package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.location.country.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface CountryDao extends CrudRepository<Country, Long> {
    long count();
    List<Country> findAll();
    List<Country> findByApproved(boolean approved);
}
