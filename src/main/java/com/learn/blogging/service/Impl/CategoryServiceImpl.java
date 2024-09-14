package com.learn.blogging.service.Impl;

import com.learn.blogging.beans.CategoryDto;
import com.learn.blogging.entities.Category;
import com.learn.blogging.exceptions.ResourceFoundException;
import com.learn.blogging.exceptions.ResourceNotFoundException;
import com.learn.blogging.repository.CategoryRepo;
import com.learn.blogging.service.CategoryService;
import jakarta.validation.constraints.Size;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto, Category.class);
        Category saved=categoryRepo.findByTitle(categoryDto.getTitle());
        if(saved==null) {

            categoryRepo.save(category);
        }
        else
        {
            throw  new ResourceFoundException("Category","ID", saved.getId());
        }


//        System.out.println(categoryRepo.findById(100));
        return  modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        categoryRepo.save(category);
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(int id) {
        Optional<Category> op=categoryRepo.findById(id);
        op.orElseThrow(()->new ResourceNotFoundException("Category","Id",id));
        Category category=op.get();
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories=categoryRepo.findAll();
        List<CategoryDto> list=categories.stream().map((c)->this.modelMapper.map(c,CategoryDto.class)).collect(Collectors.toList());
        return list;
    }

    @Override
    public boolean deleteCategoryById(int id) {

        Optional<Category> category=categoryRepo.findById(id);
        category.orElseThrow(()->new ResourceNotFoundException("Category","ID",id));
        categoryRepo.deleteById(id);
        return true;
    }
}
