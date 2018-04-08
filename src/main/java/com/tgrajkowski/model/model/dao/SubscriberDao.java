package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.newsletter.Subscriber;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface SubscriberDao extends CrudRepository<Subscriber, Long> {
    Subscriber findByEmail(String email);
    Subscriber findByCode(String code);
    List<Subscriber> findAll();
}
