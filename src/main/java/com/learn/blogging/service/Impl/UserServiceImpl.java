package com.learn.blogging.service.Impl;

import com.learn.blogging.beans.UserDto;
import com.learn.blogging.entities.User;
import com.learn.blogging.exceptions.*;
import com.learn.blogging.repository.UserRepo;
import com.learn.blogging.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
   private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {
          User savedUser=beanToEntity(userDto);
          if(userRepo.findByUsername(userDto.getUsername())==null) {

              userRepo.save(savedUser);
              UserDto userDto1 = entityToBean(savedUser);
              return userDto1;
          }
          else
          {
              throw new  ResourceFoundException("User","id",userDto.getId());
          }
    }

    @Override
    public UserDto updateUser(UserDto userDto, int id) {
        Optional<User> optionalUser=userRepo.findById(id);
         User savedUser=optionalUser.orElseThrow(()->new ResourceNotFoundException("User","userID",userDto.getId()));
         savedUser.setAbout(userDto.getAbout());
         savedUser.setUsername(userDto.getUsername());
         savedUser.setPassword(userDto.getPassword());
         savedUser.setEmail(userDto.getEmail());
         userRepo.save(savedUser);
         UserDto updated=entityToBean(savedUser);
         return updated;
    }

    @Override
    public void deleteUserById(int id) {
           Optional<User> user=userRepo.findById(id);
            user.orElseThrow(()->new ResourceNotFoundException("User","userId",id));
           userRepo.deleteById(id);


    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users= userRepo.findAll();
        List<UserDto>dtoList=new ArrayList<>();
        for(User user:users)
        {
            UserDto userDto=entityToBean(user);
            dtoList.add(userDto);
        }
        return dtoList;

    }

    @Override
    public UserDto getUserById(int id) {
        Optional<User> Saveduser=userRepo.findById(id);
        Saveduser.orElseThrow(()->new ResourceNotFoundException("User","Id",id));
        User user=Saveduser.get();
        UserDto userDto=entityToBean(user);
        return userDto;

    }

    public User beanToEntity(UserDto userDto)
    {
        User user=modelMapper.map(userDto,User.class);
        return user;

//        User user= new User();
//        user.setId(userDto.getId());
//        user.setAbout(userDto.getAbout());
//        user.setUsername(userDto.getUsername());
//        user.setPassword(userDto.getPassword());
//        user.setEmail(userDto.getEmail());
//        return user;
    }
    public UserDto entityToBean(User user)
    {
        UserDto userDto=modelMapper.map(user,UserDto.class);
        return   userDto;
//        UserDto userDto= new UserDto();
//        userDto.setId(user.getId());
//        userDto.setAbout(user.getAbout());
//        userDto.setUsername(user.getUsername());
//        userDto.setPassword(user.getPassword());
//        userDto.setEmail(user.getEmail());
//        return userDto;
    }
}
