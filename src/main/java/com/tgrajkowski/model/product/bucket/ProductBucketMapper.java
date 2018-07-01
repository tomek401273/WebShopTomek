package com.tgrajkowski.model.product.bucket;

import com.tgrajkowski.model.product.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductBucketMapper {

    @Autowired
    ProductMapper productMapper;

    public ProductBucketDto mapToProductBucketDto(ProductBucket productBucket) {
        ProductBucketDto productBucketDto = new ProductBucketDto();
        productBucketDto.setProductDto(productMapper.mapToProductDtoForBucket(productBucket.getProduct()));
        productBucketDto.setAmount(productBucket.getAmount());
        return productBucketDto;
    }

    public List<ProductBucketDto> mapToProductBucketDtoList(List<ProductBucket> productBucketList) {
        List<ProductBucketDto> productBucketDtos = new ArrayList<>();

        for (ProductBucket productBucket : productBucketList) {
            productBucketDtos.add(mapToProductBucketDto(productBucket));
        }

        return productBucketDtos;
    }

}
