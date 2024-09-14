package com.learn.blogging.beans;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.beans.JavaBean;

@Getter
@Setter
@NoArgsConstructor
@Component
public class UserDto {


    private int id;
    @Size(min = 4,max=15,message = "Username mustbe of length 4 to 10")
    private String username;
    @Email(message = "Please enter valid email")
    private String email;
    @Size(min=8,message = "Minimum of 8 characters")
    private String password;
    @NotEmpty
    @Size(max = 100,message = "Maximum length is 100 characters")
    private String about;
}
