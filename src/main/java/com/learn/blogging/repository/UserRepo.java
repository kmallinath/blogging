package com.learn.blogging.repository;

import com.learn.blogging.beans.UserDto;
import com.learn.blogging.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepo extends JpaRepository<User,Integer> {


    public User findByUsername(String username);
}
