package com.tgrajkowski.model.product;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto {

    private Long id;
    private Integer price;
    private String title;
    private String description;
    private String ImageLink;
    private int amount;
    private int bookedProduct;
    private String bucket;

    public ProductDto(Long id, Integer price, String title, String description, String imageLink) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.description = description;
        ImageLink = imageLink;
    }
}
