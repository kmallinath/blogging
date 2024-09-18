package com.learn.blogging.controller;

import com.learn.blogging.beans.PostDto;
import com.learn.blogging.dao.ApiResponse;
import com.learn.blogging.dao.FileResponse;
import com.learn.blogging.dao.PostResponse;
import com.learn.blogging.entities.Post;
import com.learn.blogging.entities.User;
import com.learn.blogging.service.CategoryService;
import com.learn.blogging.service.FileService;
import com.learn.blogging.service.PostService;
import com.learn.blogging.service.UserService;
import com.learn.blogging.utils.AppConstants;
import jakarta.servlet.http.HttpServletResponse;
import javafx.geometry.Pos;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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


    @Value("${project.image}")
    private String path;

    @PostMapping("/add/user/{userId}/category/{catId}/post")
    public ResponseEntity<PostDto> createUser(@RequestBody PostDto postDto, @PathVariable("userId")int userId, @PathVariable("catId") int catId)
    {
        PostDto postDto1= postService.createPost(postDto,userId,catId);
        return  new ResponseEntity<>(postDto1, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostsByUser(@PathVariable("userId") int userId,
                                              @RequestParam(value = "pageNumber",defaultValue = "1") int pageNumber,
                                              @RequestParam(value = "pageSize",defaultValue = "2") int pageSize

                                                        )
    {
        PostResponse postResponse=postService.getAllPostByUser(userId,pageNumber,pageSize);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable("categoryId") int categoryId,
                                                            @RequestParam(value = "pageNumber",defaultValue = "1") int pageNumber,
                                                            @RequestParam(value = "pageSize",defaultValue ="2") int pageSize
                                                            )
    {
        PostResponse postResponse=postService.getAllPostsByCategory(categoryId,pageNumber,pageSize);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @GetMapping("posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") int postId)
    {
        PostDto postDto=postService.getPostBtId(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @GetMapping("posts/getall")
    public ResponseEntity<PostResponse> getAll(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value ="pageSize",defaultValue= AppConstants.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue =AppConstants.SORT_DIR,required = false)String sortDir
    )
    {

        PostResponse postResponse=postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
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


    @GetMapping("/post/search")
    public ResponseEntity<List<PostDto>> searchByTitle(@RequestParam("title") String title)
    {
        List<PostDto>postDtoList=postService.searchByTitle(title);
        return ResponseEntity.ok(postDtoList);
    }


    //postImage upload

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<FileResponse> uploadPostImage(
            @RequestParam("imageName")MultipartFile file,
            @PathVariable("postId") int postId
            ) throws IOException {
        PostDto postDto=postService.getPostBtId(postId);
        String name= postService.uploadImageInPost(path, file);
        postDto.setImageUrl(name);
        PostDto postDto1=postService.updatePost(postDto,postId);

        return ResponseEntity.ok(new FileResponse(name,"is uploaded Succesfully"));
    }


    //download Image

    @GetMapping("/post/image/{imageName}")
    public void downloadImage(@PathVariable("imageName")String imageName,
                              HttpServletResponse response) throws IOException {
        String path1=path;
        InputStream is=postService.downloadImage(path1,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(is,response.getOutputStream());

    }


}
