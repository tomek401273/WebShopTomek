package com.tgrajkowski.model.product.bucket;

import com.tgrajkowski.model.product.ProductMapper;

import java.util.ArrayList;
import java.util.List;

public class ProductBucketMapper {
    ProductMapper productMapper = new ProductMapper();


    public ProductBucketDto mapToProductBucketDto(ProductBucket productBucket) {
        ProductBucketDto productBucketDto = new ProductBucketDto();
        productBucketDto.setProductDto(productMapper.mapToProductDto2(productBucket.getProduct()));
        productBucketDto.setAmount(productBucket.getAmount());
        return productBucketDto;
    }

    public List<ProductBucketDto> mapToProductBucketDtoList(List<ProductBucket> productBucketList) {
        List<ProductBucketDto> productBucketDtos = new ArrayList<>();

        for (ProductBucket productBucket: productBucketList) {
            productBucketDtos.add(mapToProductBucketDto(productBucket));
        }

        return productBucketDtos;
    }

}
