package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.user.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleDao extends CrudRepository<Role, Long>{
    Role findByName(String name);
    List<Role> findAll();
}
