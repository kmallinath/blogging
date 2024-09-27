package com.learn.blogging.controller;

import com.learn.blogging.beans.UserDto;
import com.learn.blogging.dao.ApiResponse;
import com.learn.blogging.entities.User;
import com.learn.blogging.service.UserService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
    public ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto user)
    {
            UserDto userDto=userService.createUser(user);
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser( @Valid @RequestBody UserDto userDto, @PathVariable("id") int id)
    {
        UserDto userDto1=userService.updateUser(userDto,id);
        return new ResponseEntity<>(userDto1,HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
   @DeleteMapping("/delete/{id}")
   public  ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") int id)
   {
       userService.deleteUserById(id);
       return new ResponseEntity<>(new ApiResponse("User with id :" +id+" is Deleted",true),HttpStatus.OK);
   }

   @GetMapping("/getAll")
   public  ResponseEntity<List<UserDto>> getAllUsers()
   {
       List<UserDto> userDtos=userService.getAllUsers();
       return new ResponseEntity<>(userDtos,HttpStatus.ACCEPTED);

   }
   @GetMapping("get/{id}")
   public ResponseEntity<UserDto> getUserById(@PathVariable("id") int id)
   {
       UserDto user=userService.getUserById(id);
       return new ResponseEntity<>(user,HttpStatus.OK);
   }

    public User beanToEntity(UserDto userDto)
    {
        User user= new User();
        user.setId(userDto.getId());
        user.setAbout(userDto.getAbout());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        return user;
    }
    public UserDto entityToBean(User user)
    {
        UserDto userDto= new UserDto();
        userDto.setId(user.getId());
        userDto.setAbout(user.getAbout());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        return userDto;
    }



}
