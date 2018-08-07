package com.codecool.flatbuddy.controller;


import com.codecool.flatbuddy.exception.InvalidUploadTypeException;
import com.codecool.flatbuddy.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.MediaType;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Controller
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @PostMapping(path = "/user/uploadpicture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadProfilePicture(@RequestParam("file") MultipartFile file) throws IOException, InvalidUploadTypeException {
        uploadService.profilePictureUpload(file);
    }
    @PostMapping(path = "/advertisement/uploadpicture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadAdvertisementPictures(@RequestParam("files") MultipartFile[] files) throws IOException {
        uploadService.rentadPictreUpload(files);
    }
}
