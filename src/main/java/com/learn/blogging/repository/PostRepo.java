package com.learn.blogging.repository;

import com.learn.blogging.entities.Category;
import com.learn.blogging.entities.Post;
import com.learn.blogging.entities.User;
import javafx.geometry.Pos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {


    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByUser(User user, Pageable pageable);

    Page<Post>findAllByCategory(Category category,Pageable pageable);

    List<Post> findByTitleContaining(String s);


    @Modifying
    @Query("DELETE FROM Post p WHERE p.id = :id")
    void deletePostById(@Param("id") int id);
}
