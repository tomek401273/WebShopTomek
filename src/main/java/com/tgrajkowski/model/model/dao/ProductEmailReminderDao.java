package com.tgrajkowski.model.model.dao;

import com.tgrajkowski.model.product.reminder.ProductEmailReminder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ProductEmailReminderDao extends CrudRepository<ProductEmailReminder, Long> {
    ProductEmailReminder findByEmail(String email);
    boolean existsByEmail(String email);
}
