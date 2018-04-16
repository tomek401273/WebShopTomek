package com.tgrajkowski.model.product.mark;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductMarkDto {
    private String login;
    private Long productId;
    private int mark;
    private BigDecimal averageMarks;
    private BigDecimal countMarks;
}
