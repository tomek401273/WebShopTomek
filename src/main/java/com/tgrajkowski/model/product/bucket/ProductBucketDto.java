package com.tgrajkowski.model.product.bucket;

import com.tgrajkowski.model.model.dao.BucketDao;
import com.tgrajkowski.model.product.ProductDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductBucketDto {
    private ProductDto productDto;
    private int amount;
}
