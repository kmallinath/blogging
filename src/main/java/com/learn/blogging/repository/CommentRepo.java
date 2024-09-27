package com.learn.blogging.repository;

import com.learn.blogging.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {


}
