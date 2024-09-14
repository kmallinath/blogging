package com.learn.blogging.controller;

import com.learn.blogging.beans.PostDto;
import com.learn.blogging.dao.ApiResponse;
import com.learn.blogging.entities.Post;
import com.learn.blogging.entities.User;
import com.learn.blogging.service.CategoryService;
import com.learn.blogging.service.PostService;
import com.learn.blogging.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add/user/{userId}/category/{catId}/post")
    public ResponseEntity<PostDto> createUser(@RequestBody PostDto postDto, @PathVariable("userId")int userId, @PathVariable("catId") int catId)
    {
        PostDto postDto1= postService.createPost(postDto,userId,catId);
        return  new ResponseEntity<>(postDto1, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") int userId)
    {
        return new ResponseEntity<>(postService.getAllPostByUser(userId),HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") int categoryId)
    {
        return new ResponseEntity<>(postService.getAllPostsByCategory(categoryId),HttpStatus.OK);
    }

    @GetMapping("posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") int postId)
    {
        PostDto postDto=postService.getPostBtId(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @GetMapping("posts/getall")
    public ResponseEntity<List<PostDto>> getAll()
    {

        List<PostDto>posts=postService.getAllPosts();
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @DeleteMapping("/post/delete/{id}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable("id") int id)
    {
        postService.deletePostById(id);
        return new ResponseEntity<>(new ApiResponse("Post is Deleted",true),HttpStatus.OK);
    }

    @PutMapping("/post/update/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("id") int id)
    {
        PostDto postDto1=postService.updatePost(postDto,id);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }

}
