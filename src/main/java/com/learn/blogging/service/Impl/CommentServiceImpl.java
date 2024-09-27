package com.learn.blogging.service.Impl;

import com.learn.blogging.beans.CommentDto;
import com.learn.blogging.beans.PostDto;
import com.learn.blogging.entities.Comment;
import com.learn.blogging.entities.Post;
import com.learn.blogging.repository.CommentRepo;
import com.learn.blogging.service.CommentService;
import com.learn.blogging.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommentRepo commentRepo;
    @Override
    public CommentDto addComment(int postId, CommentDto commentDto) {
          PostDto postDto=postService.getPostBtId(postId);
          Comment comment=modelMapper.map(commentDto, Comment.class);
          Post post =modelMapper.map(postDto,Post.class);
          comment.setPost(post);
          Comment saved=commentRepo.save(comment);
          return modelMapper.map(saved,CommentDto.class);

    }

    @Override
    public boolean deleteCommentById(int id) {
        return false;
    }
}
