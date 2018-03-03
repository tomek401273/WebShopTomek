package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface UserDao extends CrudRepository<User, Long> {
    public User findByLogin(String login);
}
