package com.tgrajkowski.model.product.category;

import com.tgrajkowski.model.product.ProductDto;
import com.tgrajkowski.model.product.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {
    @Autowired
    private ProductMapper productMapper;

    public CategoryDto mapToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto(category.id, category.name);
        List<ProductDto> productDtoList = productMapper.mapToProductDtoList(category.getProductList());
        categoryDto.setProductDtoList(productDtoList);
        return categoryDto;
    }
}
