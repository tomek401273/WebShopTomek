package com.tgrajkowski.model.product;


import com.tgrajkowski.model.model.dao.CategoryDao;
import com.tgrajkowski.model.model.dao.ProductStatusDao;
import com.tgrajkowski.model.product.Product;
import com.tgrajkowski.model.product.ProductDto;
import com.tgrajkowski.model.product.ProductStatus;
import com.tgrajkowski.model.product.bucket.ProductBucketDto;
import com.tgrajkowski.model.product.category.Category;
import com.tgrajkowski.model.product.comment.CommentMapper;
import com.tgrajkowski.model.product.order.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ProductMapper {
    private CommentMapper commentMapper = new CommentMapper();

    public Product mapToProduct(ProductDto productDto, ProductStatus productStatus, Category category) {
        Product product = new Product(
                productDto.getPrice(),
                productDto.getTitle(),
                productDto.getDescription(),
                productDto.getImageLink(),
                productDto.getTotalAmount(),
                productDto.getTotalAmount(),
                new Date());
        product.setStatus(productStatus);
        product.setCategory(category);
        return product;
    }

    public Product mapToProduct(Product product, ProductDto productDto, ProductStatus productStatus) {
        product.setPrice(productDto.getPrice());
        product.setImageLink(productDto.getImageLink());
        product.setDescription(productDto.getDescription());
        product.setAvailableAmount(productDto.getTotalAmount());
        product.setTotalAmount(productDto.getTotalAmount());
        product.setTitle(productDto.getTitle());
        product.setStatus(productStatus);
        product.setLastModification(new Date());
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
                product.getAvailableAmount()
        );
        productDto.setStatusCode(product.getStatus().code);
        productDto.setStatusMessage(product.getStatus().name);
        productDto.setSumMarks(product.getSumMarks());
        productDto.setCountMarks(product.getCountMarks());
        productDto.setMarksAverage(product.getAverageMarks());
        productDto.setCommentDtos(commentMapper.mapToCommentDtos(product.getComments()));

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

    public ProductDto mapToProductDto2(Product product) {
        ProductDto productDto = new ProductDto(
                product.getId(),
                product.getPrice(),
                product.getTitle(),
                product.getDescription(),
                product.getImageLink(),
                product.getTotalAmount(),
                product.getAvailableAmount()
        );
        productDto.setStatusCode(product.getStatus().code);
        productDto.setStatusMessage(product.getStatus().name);
        productDto.setSumMarks(product.getSumMarks());
        productDto.setCountMarks(product.getCountMarks());
        productDto.setMarksAverage(product.getAverageMarks());
        productDto.setDirectLink("http://localhost:4200/product/"+product.getId());
        return productDto;
    }
}
