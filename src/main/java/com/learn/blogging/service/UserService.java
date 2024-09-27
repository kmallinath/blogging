package com.learn.blogging.service;

import com.learn.blogging.beans.UserDto;

import java.util.List;


public interface UserService{
    public UserDto createUser(UserDto user);

    UserDto updateUser(UserDto userDto, int id);

    public void deleteUserById(int id);
    public List<UserDto> getAllUsers();
    public UserDto getUserById(int id);

    UserDto registerUser(UserDto userDto);
}
