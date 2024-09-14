package com.learn.blogging.repository;

import com.learn.blogging.entities.Category;
import com.learn.blogging.entities.Post;
import com.learn.blogging.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {


    List<Post> findAllByUser(User user);

    List<Post>findAllByCategory(Category category);


}
