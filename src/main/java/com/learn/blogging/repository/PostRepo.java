package com.learn.blogging.repository;

import com.learn.blogging.entities.Category;
import com.learn.blogging.entities.Post;
import com.learn.blogging.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {


    Page<Post> findAllByUser(User user, Pageable pageable);

    Page<Post>findAllByCategory(Category category,Pageable pageable);

    List<Post> findByTitleContaining(String s);


}
