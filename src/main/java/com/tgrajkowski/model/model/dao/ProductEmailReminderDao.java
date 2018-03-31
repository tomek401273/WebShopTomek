package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.reminder.Reminder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ProductEmailReminderDao extends CrudRepository<Reminder, Long> {
    Reminder findByEmail(String email);
    boolean existsByEmail(String email);
}
