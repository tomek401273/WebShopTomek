package com.tgrajkowski.model.product;


import com.tgrajkowski.model.model.dao.ProductStatusDao;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.ProductDto;
import com.tgrajkowski.model.product.ProductStatus;
import com.tgrajkowski.model.product.bucket.ProductBucketDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ProductMapper {

    @Autowired
    private ProductStatusDao productStatusDao;

    public Product mapToProduct(ProductDto productDto) {
        Product product = new Product(
                productDto.getPrice(),
                productDto.getTitle(),
                productDto.getDescription(),
                productDto.getImageLink(),
                productDto.getTotalAmount(),
                productDto.getTotalAmount());
        return product;
    }

    public Product mapToProduct(Product product, ProductDto productDto) {
        product.setPrice(productDto.getPrice());
        product.setImageLink(productDto.getImageLink());
        product.setDescription(productDto.getDescription());
        product.setAvailableAmount(productDto.getTotalAmount());
        product.setTotalAmount(productDto.getTotalAmount());
        product.setTitle(productDto.getTitle());
        ProductStatus productStatus = productStatusDao.findProductStatusByCode(productDto.getStatusCode());
        product.setStatus(productStatus);
        return product;
    }

    public ProductDto mapToProductDto(Product product) {
        ProductDto productDto = new ProductDto(
                product.getId(),
                product.getPrice(),
                product.getTitle(),
                product.getDescription(),
                product.getImageLink(),
                product.getTotalAmount(),
                product.getAvailableAmount(),
                product.getStatus().code,
                product.getStatus().name

        );

        return productDto;
    }


    public List<ProductDto> mapToProductDtoList(List<Product> products) {
        List<ProductDto> dtoList = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = mapToProductDto(product);
            dtoList.add(productDto);
        }
        return dtoList;
    }

    public ProductBucketDto maptoProcuctBucketDto(Product product){
//        ProductBucketDto bucketDto = new ProductBucketDto();
//        bucketDto.setId(product.getId());
//        bucketDto.setAmount(0);
//        bucketDto.setDescription(product.getDescription());
//        bucketDto.setImageLink(product.getImageLink());
//        bucketDto.setTotalPrice(product.getTotalPrice());
//        bucketDto.setTitle(product.getTitle());
//        return bucketDto;
        return null;
    }

}
