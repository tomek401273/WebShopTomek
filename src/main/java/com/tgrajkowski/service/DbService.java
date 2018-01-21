package com.tgrajkowski.service;

import com.tgrajkowski.model.Product;
import com.tgrajkowski.model.model.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbService {
    @Autowired
    private ProductDao productDao;

    public Product saveProduct(final Product product) {
        return productDao.save(product);
    }
}
