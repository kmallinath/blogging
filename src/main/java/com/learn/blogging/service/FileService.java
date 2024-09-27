package com.learn.blogging.service;

import com.learn.blogging.dao.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    public FileResponse uploadImage(String path, MultipartFile file) throws IOException;

    public InputStream downloadImage(String path, String imageName) throws FileNotFoundException;



}
