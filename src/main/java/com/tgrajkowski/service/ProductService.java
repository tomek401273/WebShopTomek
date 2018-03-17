package com.tgrajkowski.service;

import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.ProductDto;
import com.tgrajkowski.model.product.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

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
    }

    public void updateTask(ProductDto productDto) {
        Product product = productDao.findById(productDto.getId());
    }

    public Product saveProduct(ProductDto productDto) {
        Product product = mapper.mapToProduct(productDto);
        int amount = productDto.getAmount();

        return productDao.save(product);
    }

    public ProductDto getOneProduct(Long id) {
        ProductDto productDto = mapper.mapToProductDto(productDao.findById(id));
        return productDto;
    }
}
