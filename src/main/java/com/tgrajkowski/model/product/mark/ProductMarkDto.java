package com.tgrajkowski.model.product.mark;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductMarkDto {
    private String login;
    private Long productId;
    private int mark;
}
