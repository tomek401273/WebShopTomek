package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.user.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface UserDao extends CrudRepository<Users, Long> {
     Users findByLogin(String login);
     List<Users> findAll();
}
