package com.tgrajkowski.model.product;


import com.tgrajkowski.model.product.bucket.ProductBucketDto;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

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

//    public Product mapToProductWithId(ProductDto productDto) {
//        Product product = new Product(
//                productDto.getTotalPrice(),
//                productDto.getTitle(),
//                productDto.getDescription(),
//                productDto.getImageLink());
//        product.setId(productDto.getId());
//        return product;
//    }

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
