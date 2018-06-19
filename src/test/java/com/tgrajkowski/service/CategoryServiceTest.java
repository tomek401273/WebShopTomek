package com.tgrajkowski.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tgrajkowski.model.model.dao.CategoryDao;
import com.tgrajkowski.model.product.category.Category;
import com.tgrajkowski.model.product.category.CategoryDto;
import com.tgrajkowski.model.product.category.CategoryMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @InjectMocks
    CategoryService categoryService;

    @Mock
    private CategoryDao categoryDao;

    @Mock
    private CategoryMapper categoryMapper;

    @Test
    public void getAllCategoryName() {
        // Given
        List<Category> categoryList = new ArrayList<>();
        Category category1 = new Category();
        category1.setName("category1");
        Category category2 = new Category();
        category2.setName("category2");
        Category category3 = new Category();
        category3.setName("category3");
        categoryList.add(category1);
        categoryList.add(category2);
        categoryList.add(category3);
        Mockito.when(categoryDao.findAll()).thenReturn(categoryList);
        // When
        List<String> retrieved = categoryService.getAllCategoryName();
        Assert.assertEquals(3, retrieved.size());
        Assert.assertEquals("category1", retrieved.get(0));
        Assert.assertEquals("category2", retrieved.get(1));
        Assert.assertEquals("category3", retrieved.get(2));
    }

    @Test
    public void getAllCategoryNameEmptyList() {
        // Given
        List<Category> categoryList = new ArrayList<>();
        Mockito.when(categoryDao.findAll()).thenReturn(categoryList);
        // When
        List<String> retrieved = categoryService.getAllCategoryName();
        // Then
        Assert.assertEquals(0, retrieved.size());
    }

    @Test
    public void getProductWithCategory() {
        // Given
        Category category = new Category();
        category.setName("category");
        category.setId((long)1);
        String categoryName = "category";
        Mockito.when(categoryDao.findByName(categoryName)).thenReturn(category);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId((long)1);
        categoryDto.setName("category");
        Mockito.when(categoryMapper.mapToCategoryDto(category)).thenReturn(categoryDto);

        // When
        CategoryDto retrieved = categoryService.getProductWithCategory(categoryName);
        System.out.println(retrieved);
        // Then
        long idRetrieved = retrieved.getId();
        Assert.assertEquals((long)1, idRetrieved);
        Assert.assertEquals(categoryName, retrieved.getName());
    }

    @Test
    public void getProductWithCategoryNotFound() {
        // Given
        Mockito.when(categoryDao.findByName("category")).thenReturn(null);
        // When
        CategoryDto categoryDto= categoryService.getProductWithCategory("category");
        // Then
        Assert.assertNull(categoryDto.getId());
        Assert.assertNull(categoryDto.getName());
        Assert.assertEquals(0, categoryDto.getProductDtoList().size());
    }
}