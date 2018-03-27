package com.tgrajkowski.model.product;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class FilterPrice {
    private int above;
    private int below;
}
