package com.tgrajkowski.model.product;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProductDto {

    private Long id;
    @Min(0)
    private Integer price;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String ImageLink;
    @Min(0)
    private int totalAmount;
    @Min(0)
    private int availableAmount;
}
