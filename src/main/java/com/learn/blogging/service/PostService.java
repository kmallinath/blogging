package com.learn.blogging.service;

import com.learn.blogging.beans.PostDto;
import com.learn.blogging.dao.FileResponse;
import com.learn.blogging.dao.PostResponse;
import com.learn.blogging.entities.Category;
import com.learn.blogging.entities.User;
import com.learn.blogging.repository.PostRepo;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public interface PostService {


    public PostDto createPost(PostDto postDto, int userId, int categoryId);

    public PostResponse getAllPostByUser(int userId, int pageNumber, int pageSize);

    public PostResponse getAllPostsByCategory(int categoryId, int pageNumber, int pageSize);

    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);

    public PostDto getPostBtId(int postId);

    public boolean deletePostById(int id);

    public PostDto updatePost(PostDto postDto, int id);

    public List<PostDto> searchByTitle(String value);


    public String uploadImageInPost(String path, MultipartFile file) throws IOException;

    public InputStream downloadImage(String path,String imageName) throws FileNotFoundException;
}
