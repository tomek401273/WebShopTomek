package com.tgrajkowski.service;

import com.tgrajkowski.model.model.dao.CategoryDao;
import com.tgrajkowski.model.product.category.Category;
import com.tgrajkowski.model.product.category.CategoryDto;
import com.tgrajkowski.model.product.category.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryMapper categoryMapper;

    public List<String> getAllCategoryName() {
        List<String> categoryAllNames = new ArrayList<>();
        List<Category> categoryList = categoryDao.findAll();
        for(Category category: categoryList) {
            categoryAllNames.add(category.getName());
        }
        return categoryAllNames;
    }

    public CategoryDto getProductWithCategory(String categoryName) {
        CategoryDto categoryDto = new CategoryDto();
        Optional<Category> category = Optional.ofNullable(categoryDao.findByName(categoryName));
        if(category.isPresent()){
            categoryDto = categoryMapper.mapToCategoryDto(category.get());
        }
        return categoryDto;
    }
}
