package com.codecool.flatbuddy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class UploadController {
    @Value("${upload.path}")
    private String path;
    @PostMapping(path = "/user/uploadpicture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

            File uploadedFile = new File(path, file.getOriginalFilename());

            uploadedFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(uploadedFile);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();

        return new ResponseEntity<Object>("file Uplaoded succesfully", HttpStatus.OK);
    }
}
