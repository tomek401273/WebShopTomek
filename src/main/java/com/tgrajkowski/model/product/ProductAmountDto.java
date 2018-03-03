package com.tgrajkowski.model.product;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductAmountDto {
    private Long id;
    private Product product;
    private Bucket bucket;
}
