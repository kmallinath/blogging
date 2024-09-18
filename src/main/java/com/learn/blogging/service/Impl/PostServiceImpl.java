package com.learn.blogging.service.Impl;

import com.learn.blogging.beans.CategoryDto;
import com.learn.blogging.beans.PostDto;
import com.learn.blogging.beans.UserDto;
import com.learn.blogging.dao.ApiResponse;
import com.learn.blogging.dao.FileResponse;
import com.learn.blogging.dao.PostResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
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
    public PostResponse getAllPostByUser(int userId,int pageNumber,int pageSize) {
        User user= userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","ID",userId));

        Pageable pageable=PageRequest.of(pageNumber,pageSize);

        Page<Post> pages=postRepo.findAllByUser(user,pageable);
        if(pages.getNumberOfElements()==0) throw new ResourceNotFoundException("Posts","for User",userId);
        List<PostDto>ans=new ArrayList<>();
        for(Post post:pages.getContent())
        {

            UserDto userDto=modelMapper.map(post.getUser(),UserDto.class);
            CategoryDto categoryDto=modelMapper.map(post.getCategory(), CategoryDto.class);
            PostDto postDto=modelMapper.map(post,PostDto.class);
            postDto.setCategoryDto(categoryDto);
            postDto.setUserDto(userDto);
            postDto.setAddeddate(post.getAddeddate());
            ans.add(postDto);
        }
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(ans);
        postResponse.setPageNumber(pages.getNumber());
        postResponse.setPageSize(pages.getSize());
        postResponse.setTotalElements(pages.getTotalElements());
        postResponse.setTotalPages(pages.getTotalPages());
        postResponse.setLast(pages.isLast());
        return postResponse;
    }

    @Override
    public PostResponse getAllPostsByCategory(int categoryId, int pageNumber, int pageSize) {
        Category category=categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","ID",categoryId));

        Pageable pageable=PageRequest.of(pageNumber,pageSize);
       Page<Post> pages=postRepo.findAllByCategory(category,pageable);
       if(pages.getNumberOfElements()==0) throw new ResourceNotFoundException("Posts","for Category", category.getId());
       List<PostDto>ans=new ArrayList<>();
       for(Post post:pages.getContent())
       {
           UserDto userDto=modelMapper.map(post.getUser(),UserDto.class);
           CategoryDto categoryDto=modelMapper.map(post.getCategory(), CategoryDto.class);
           PostDto postDto=modelMapper.map(post,PostDto.class);
           postDto.setCategoryDto(categoryDto);
           postDto.setUserDto(userDto);
           postDto.setAddeddate(post.getAddeddate());
           ans.add(postDto);
       }
       PostResponse postResponse=new PostResponse();
       postResponse.setTotalElements(pages.getTotalElements());
       postResponse.setContent(ans);
       postResponse.setLast(pages.isLast());
       postResponse.setTotalPages(pages.getTotalPages());
       postResponse.setPageSize(pages.getSize());
       return postResponse;
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

    public PostResponse getAllPosts(int pageNumber, int pageSize,String sortBy,String sortDir)
    {
        Sort sort;
        if(sortDir.equals("asc")) {
            sort=Sort.by(sortBy).ascending();
        }
        else
            sort=Sort.by(sortBy).descending();

        Pageable p= PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pageList=postRepo.findAll(p);
        List<Post>list=pageList.getContent();
        if(list.size()==0)
            throw new ResourceNotFoundException("Posts","PageNumber",pageNumber);
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
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNumber(pageList.getNumber());
        postResponse.setPageSize(pageList.getSize());
        postResponse.setTotalPages(pageList.getTotalPages());
        postResponse.setTotalElements(pageList.getTotalElements());
        postResponse.setLast(pageList.isLast());

        return  postResponse;
    }

    //Search Operation

    public List<PostDto> searchByTitle(String value)
    {
        List<Post> posts=postRepo.findByTitleContaining(value);
        List<PostDto> postDtoList=new ArrayList<>();
        for(Post p:posts)
        {
            UserDto userDto=modelMapper.map(p.getUser(),UserDto.class);
            CategoryDto categoryDto=modelMapper.map(p.getCategory(),CategoryDto.class);
            PostDto postDto=modelMapper.map(p,PostDto.class);
            postDto.setCategoryDto(categoryDto);
            postDto.setUserDto(userDto);
            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    @Override
    public String uploadImageInPost(String path, MultipartFile file) throws IOException {


        String a=path+file.getOriginalFilename();
        String randomId= UUID.randomUUID().toString();
        String finalImageName=randomId.concat(a.substring(a.lastIndexOf(".")));

        String totalPath=path+File.separator+finalImageName;
        File f=new File(path);
        if(!f.exists())
        {
            f.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(totalPath));

        return finalImageName;
    }

    @Override
    public InputStream downloadImage(String path,String imageName) throws FileNotFoundException {
        String fullPath=path+File.separator+imageName;
        InputStream is=new FileInputStream(fullPath);

        return is;
    }


}
