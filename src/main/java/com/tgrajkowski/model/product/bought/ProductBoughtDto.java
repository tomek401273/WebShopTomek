package com.tgrajkowski.model.product.bought;

import com.tgrajkowski.model.product.ProductDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductBoughtDto {
    private ProductDto product;
    private int amount;
    private int totalPrice;
}
