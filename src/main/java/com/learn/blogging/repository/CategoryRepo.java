package com.learn.blogging.repository;

import com.learn.blogging.entities.Category;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {


    Category findByTitle(@Size(min=4,max=10,message = "Title length should be more than 4 and less than 10 characters") String title);
}
