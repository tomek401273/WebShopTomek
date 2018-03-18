package com.tgrajkowski.model.product;

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
