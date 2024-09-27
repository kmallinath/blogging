package com.learn.blogging.controller;


import com.learn.blogging.dao.FileResponse;
import com.learn.blogging.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    public String pathValue;
    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadImage(
                            @RequestParam("file")MultipartFile file) throws IOException {
            FileResponse fileResponse=fileService.uploadImage(pathValue,file);
            return ResponseEntity.ok(fileResponse);
    }

    //Download/Serve Images

    @GetMapping("/profiles/{imageName}")
    public void getImages(@PathVariable("imageName")String imageName,
                          HttpServletResponse response) throws IOException {
        InputStream is=fileService.downloadImage(pathValue,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(is,response.getOutputStream());

    }
}
