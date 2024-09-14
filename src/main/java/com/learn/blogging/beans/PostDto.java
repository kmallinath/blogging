package com.learn.blogging.beans;

import com.learn.blogging.entities.Category;
import com.learn.blogging.entities.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {


    private int id;
    private String title;
    private  String content;
    private   String imageUrl;
    private Date Addeddate;
    private UserDto userDto;
    private CategoryDto categoryDto;
}
