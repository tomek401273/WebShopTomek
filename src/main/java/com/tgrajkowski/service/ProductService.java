package com.tgrajkowski.service;

import com.tgrajkowski.model.bucket.ProductBucketDto;
import com.tgrajkowski.model.bucket.UserBucketDto;
import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.model.dao.ProductAmountDao;
import com.tgrajkowski.model.model.dao.UserDao;
import com.tgrajkowski.model.product.*;
import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.user.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.boot.jaxb.SourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.*;

import org.hibernate.Criteria;
import org.hibernate.Session;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductAmountDao productAmountDao;
    ProductMapper mapper = new ProductMapper();

    public int checkAvailable(Long id) {
        Product product = productDao.findById(id);
        ProductDto productDto = mapper.mapToProductDto(product);
        int allProducts = productDto.getAmount();
        int bookedProducts = productDto.getBookedProduct();
        int availableProducts = allProducts - bookedProducts;
        return availableProducts;
    }

    public List<ProductDto> getProducts() {
        List<Product> products = productDao.findAll();
        return mapper.mapToProductDtoList(products);
    }

    public void removeProductFromDatabase(ProductDto productDto) {
        Product product = productDao.findById(productDto.getId());
        List<ProductAmount> amountList = product.getProductAmounts();
        for (ProductAmount productAmount : amountList) {
            productAmount.setProduct(null);
        }
        product.setProductAmounts(amountList);
        productDao.save(product);
        productDao.deleteById(productDto.getId());
        List<ProductAmount> productAmountL = productAmountDao.findByProductId(null);
        productAmountDao.delete(productAmountL);
    }

    public void updateTask(ProductDto productDto) {
        Product product = productDao.findById(productDto.getId());
        List<ProductAmount> amountList = product.getProductAmounts();
        for (ProductAmount productAmount : amountList) {
            productAmount.setProduct(null);
            productAmountDao.save(productAmount);
            productAmountDao.delete(productAmount);
        }
        product.setProductAmounts(amountList);
        productDao.save(product);

        Product product2 = productDao.findById(productDto.getId());
        product2.setTitle(productDto.getTitle());
        product2.setDescription(productDto.getDescription());
        product2.setImageLink(productDto.getImageLink());
        product2.setPrice(productDto.getPrice());
        List<ProductAmount> amountList2 = new ArrayList<>();
        for (int i = 0; i < productDto.getAmount(); i++) {
            ProductAmount productAmount2 = new ProductAmount();
            productAmount2.setProduct(product2);
            productAmountDao.save(productAmount2);
            amountList2.add(productAmount2);
        }
        product2.setProductAmounts(amountList2);
        productDao.save(product2);
    }

    public Product saveProduct(ProductDto productDto) {
        Product product = mapper.mapToProduct(productDto);
        int amount = productDto.getAmount();
        for (int i = 0; i < amount; i++) {
            ProductAmount productAmount = new ProductAmount();
            productAmount.setProduct(product);
            product.getProductAmounts().add(productAmount);
        }
        return productDao.save(product);
    }

    public ProductDto getOneProduct(Long id) {
        ProductDto productDto = mapper.mapToProductDto(productDao.findById(id));
        return productDto;
    }
}
