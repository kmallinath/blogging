package com.learn.blogging.service.Impl;

import com.learn.blogging.beans.CategoryDto;
import com.learn.blogging.beans.PostDto;
import com.learn.blogging.beans.UserDto;
import com.learn.blogging.dao.ApiResponse;
import com.learn.blogging.entities.Category;
import com.learn.blogging.entities.Post;
import com.learn.blogging.entities.User;
import com.learn.blogging.exceptions.ResourceFoundException;
import com.learn.blogging.exceptions.ResourceNotFoundException;
import com.learn.blogging.repository.CategoryRepo;
import com.learn.blogging.repository.PostRepo;
import com.learn.blogging.repository.UserRepo;
import com.learn.blogging.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto,int userId,int categoryId) {

        User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","ID",userId));
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
        Post post=modelMapper.map(postDto,Post.class);
        post.setUser(user);
         post.setCategory(category);
        post.setAddeddate(new Date());
        Post post1=postRepo.save(post);

        PostDto postDto1=modelMapper.map(post1,PostDto.class);

        postDto1.setUserDto(modelMapper.map(user, UserDto.class));
        postDto1.setCategoryDto(modelMapper.map(category, CategoryDto.class));

        return postDto1;
    }

    @Override
    public List<PostDto> getAllPostByUser(int userId) {
        User user= userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","ID",userId));
        List<Post>posts=postRepo.findAllByUser(user);
        if(posts.size()==0) throw new ResourceNotFoundException("Posts","for User",userId);
        List<PostDto>ans=new ArrayList<>();
        for(Post post:posts)
        {

            UserDto userDto=modelMapper.map(post.getUser(),UserDto.class);
            CategoryDto categoryDto=modelMapper.map(post.getCategory(), CategoryDto.class);
            PostDto postDto=modelMapper.map(post,PostDto.class);
            postDto.setCategoryDto(categoryDto);
            postDto.setUserDto(userDto);
            postDto.setAddeddate(post.getAddeddate());
            ans.add(postDto);
        }
        return ans;
    }

    @Override
    public List<PostDto> getAllPostsByCategory(int categoryId) {
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","ID",categoryId));
       List<Post> posts=postRepo.findAllByCategory(category);
       if(posts.size()==0) throw new ResourceNotFoundException("Posts","for Category", category.getId());
       List<PostDto>ans=new ArrayList<>();
       for(Post post:posts)
       {
           UserDto userDto=modelMapper.map(post.getUser(),UserDto.class);
           CategoryDto categoryDto=modelMapper.map(post.getCategory(), CategoryDto.class);
           PostDto postDto=modelMapper.map(post,PostDto.class);
           postDto.setCategoryDto(categoryDto);
           postDto.setUserDto(userDto);
           postDto.setAddeddate(post.getAddeddate());
           ans.add(postDto);
       }
       return ans;

    }

    @Override
    public PostDto getPostBtId(int postId) {
        Post post= postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","ID",postId));
        UserDto userDto=modelMapper.map(post.getUser(),UserDto.class);
        CategoryDto categoryDto=modelMapper.map(post.getCategory(), CategoryDto.class);
        PostDto postDto=modelMapper.map(post,PostDto.class);
        postDto.setUserDto(userDto);
        postDto.setCategoryDto(categoryDto);
        return postDto;
    }

    @Override
    public boolean deletePostById(int id) {
        Post post= postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("POST","ID",id));
        postRepo.deleteById(id);
        return true;
    }

    @Override
    public PostDto updatePost(PostDto postDto, int id) {
        Post post=postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Id",id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageUrl(postDto.getImageUrl());
        Post saved=postRepo.save(post);
        UserDto userDto=modelMapper.map(saved.getUser(),UserDto.class);
        CategoryDto categoryDto=modelMapper.map(saved.getCategory(),CategoryDto.class);
        PostDto updatedPost=modelMapper.map(saved,PostDto.class);
        updatedPost.setUserDto(userDto);
        updatedPost.setCategoryDto(categoryDto);
        return  updatedPost;
    }

    public List<PostDto> getAllPosts()
    {
        List<Post>list=postRepo.findAll();
        if(list.size()==0)
            throw new ResourceFoundException("Posts are","not available. Count:",0);
         List<PostDto> postDtoList=new ArrayList<>();
        for(Post post: list)
        {
             UserDto userDto=modelMapper.map(post.getUser(),UserDto.class);
             CategoryDto categoryDto=modelMapper.map(post.getCategory(),CategoryDto.class);
             PostDto postDto=modelMapper.map(post,PostDto.class);
             postDto.setCategoryDto(categoryDto);
             postDto.setUserDto(userDto);
             postDtoList.add(postDto);

        }

        return  postDtoList;
    }


}
