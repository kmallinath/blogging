package com.learn.blogging.service;

import com.learn.blogging.beans.CommentDto;
import com.learn.blogging.dao.ApiResponse;

public interface CommentService {

    public CommentDto addComment(int postId,CommentDto commentDto);

    public boolean deleteCommentById(int id);
}
