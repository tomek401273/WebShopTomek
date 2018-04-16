package com.tgrajkowski.controller;

import com.tgrajkowski.model.product.category.CategoryDto;
import com.tgrajkowski.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<String> getAllCategory() {
        return categoryService.getAllCategoryName();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/product")
    public CategoryDto getProductWithCategory(@RequestParam String category) {
        return categoryService.getProductWithCategory(category);
    }
}
