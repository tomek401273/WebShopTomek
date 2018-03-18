package com.tgrajkowski.model.product;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private Integer price;
    private String title;
    private String description;
    private String ImageLink;
    private int totalAmount;
    private int availableAmount;


}
