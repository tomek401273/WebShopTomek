package com.tgrajkowski.model.product.category;

import com.tgrajkowski.model.product.ProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CategoryDto {
    private Long id;
    private String name;
    private List<ProductDto> productDtoList = new ArrayList<>();

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
