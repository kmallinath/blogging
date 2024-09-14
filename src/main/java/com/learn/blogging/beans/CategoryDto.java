package com.learn.blogging.beans;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private  int id;

    @Size(min=4,max=10,message = "Title length should be more than 4 and less than 10 characters")
    private String title;

    @NotEmpty
    private String description;
}
