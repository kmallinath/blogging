package com.learn.blogging.controller;


import com.learn.blogging.beans.CategoryDto;
import com.learn.blogging.beans.UserDto;
import com.learn.blogging.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        CategoryDto categoryDto1=categoryService.createCategory(categoryDto);
        return  new ResponseEntity(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public  ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        CategoryDto categoryDto1=categoryService.updateCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1,HttpStatus.ACCEPTED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDto>> getAllCategories()
    {
        List<CategoryDto> categoryDtoList=categoryService.getAllCategories();
        return new ResponseEntity<>(categoryDtoList,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteCategoryById(@PathVariable("id") int id)
    {
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable("id") int id)
    {
        CategoryDto category=categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }


}


