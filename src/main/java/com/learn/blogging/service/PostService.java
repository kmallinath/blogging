package com.learn.blogging.service;

import com.learn.blogging.beans.PostDto;
import com.learn.blogging.entities.Category;
import com.learn.blogging.entities.User;
import com.learn.blogging.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostService {



    public PostDto createPost(PostDto postDto,int userId,int categoryId);

    public List<PostDto> getAllPostByUser(int userId);

    public List<PostDto> getAllPostsByCategory(int categoryId);

    public List<PostDto> getAllPosts();

    public PostDto getPostBtId(int postId);

    public boolean deletePostById(int id);

    public PostDto updatePost(PostDto postDto, int id);
}
