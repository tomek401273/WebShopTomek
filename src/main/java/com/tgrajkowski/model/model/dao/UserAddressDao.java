package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.user.UserAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface UserAddressDao extends CrudRepository<UserAddress, Long> {
}
