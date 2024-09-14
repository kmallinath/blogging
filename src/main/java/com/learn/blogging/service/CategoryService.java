package com.learn.blogging.service;

import com.learn.blogging.beans.CategoryDto;

import java.util.List;


public interface CategoryService {

    public CategoryDto createCategory(CategoryDto categoryDto);

    public CategoryDto updateCategory(CategoryDto categoryDto);


    CategoryDto getCategoryById(int id);

    public List<CategoryDto> getAllCategories();

    public boolean deleteCategoryById(int id);

}
