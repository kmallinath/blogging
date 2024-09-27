package com.learn.blogging.controller;

import com.learn.blogging.beans.CommentDto;
import com.learn.blogging.dao.ApiResponse;
import com.learn.blogging.entities.Comment;
import com.learn.blogging.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("post/addComment/{postId}")
    public CommentDto addComment(@PathVariable("postId") int postId, @RequestBody CommentDto commentDto)
    {
        CommentDto commentDto1=commentService.addComment(postId,commentDto);
        return commentDto1;
    }


}
