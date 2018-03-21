package com.tgrajkowski.service;

import com.tgrajkowski.model.model.dao.ProductDao;
import com.tgrajkowski.model.product.FilterPrice;
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
        return product.getAvailableAmount();
    }

    public List<ProductDto> getProducts() {
        List<Product> products = productDao.findAll();
        return mapper.mapToProductDtoList(products);
    }

    public void removeProductFromDatabase(ProductDto productDto) {
        productDao.deleteById(productDto.getId());
    }

    public void updateProduct(ProductDto productDto) {
        Product product = productDao.findById(productDto.getId());
        product.setPrice(productDto.getPrice());
        product.setImageLink(productDto.getImageLink());
        product.setDescription(productDto.getDescription());
        product.setAvailableAmount(productDto.getTotalAmount());
        product.setTotalAmount(productDto.getTotalAmount());
        product.setTitle(productDto.getTitle());

        productDao.save(product);
    }

    public Product saveProduct(ProductDto productDto) {
        Product product = mapper.mapToProduct(productDto);
        return productDao.save(product);
    }

    public ProductDto getOneProduct(Long id) {
        ProductDto productDto = mapper.mapToProductDto(productDao.findById(id));
        return productDto;
    }

    public List<ProductDto> searchProduct(String title) {

        return mapper.mapToProductDtoList(productDao.findProductContainstTitleWithLetters(title));
    }

    public List<ProductDto> filterProductWithPriceBetween(FilterPrice filterPrice) {
       return mapper.mapToProductDtoList(productDao.findProductWithPriceBetween(filterPrice.getAbove(),filterPrice.getBelow()));
    }
}
