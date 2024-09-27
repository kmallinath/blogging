package com.learn.blogging.service.Impl;

import com.learn.blogging.dao.FileResponse;
import com.learn.blogging.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {


    @Override
    public FileResponse uploadImage(String path, MultipartFile file) throws IOException {


        //fileName
        String imgName=file.getOriginalFilename();

        //randomName for image
        String randomId= String.valueOf(UUID.randomUUID());

        String finalImgName= randomId.concat(imgName.substring(imgName.lastIndexOf(".")));

        //Total FilePath
        String totalPath=path+ File.separator+finalImgName;

//        Create Folder if Not Created
        File f=new File(path);

        if(!f.exists())
        {
            f.mkdir();
        }

//        Copy File
        Files.copy(file.getInputStream(), Paths.get(totalPath));

        return new FileResponse(imgName,"is uploaded succesfully");
    }

    @Override
    public InputStream downloadImage(String path, String imageName) throws FileNotFoundException {
        String fullPath=path+ File.separator+imageName;
        InputStream is=new FileInputStream(fullPath);
        return is;
        }
    }


