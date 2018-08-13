package com.codecool.flatbuddy.controller;


import com.codecool.flatbuddy.exception.InvalidUploadTypeException;
import com.codecool.flatbuddy.exception.UnauthorizedException;
import com.codecool.flatbuddy.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.MediaType;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Map;

@RestController
public class RestImageController {
    @Autowired
    private ImageService imageService;
    @PostMapping(path = "/user/uploadpicture")
    public void uploadProfilePicture(@RequestParam("file") MultipartFile file) throws IOException, InvalidUploadTypeException {
        imageService.profilePictureUpload(file);
    }
    @PostMapping(path = "/advertisement/uploadpicture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String,Boolean> uploadAdvertisementPictures(@RequestParam("file") MultipartFile file, @PathVariable("id")int id) throws IOException, InvalidUploadTypeException, UnauthorizedException {
        return imageService.rentadPictreUpload(file,id);
    }
    @DeleteMapping("/user/deletepicture/{id}")
    public Map<String,Boolean> deleteProfilePicture(@PathVariable("id")int id) throws UnauthorizedException {
        return imageService.deleteProfilePicture(id);
    }
    @DeleteMapping("/advertisement/deletepicture/{id}")
    public Map<String,Boolean> deleteAdvertisementPictore(@PathVariable("id") int id) throws UnauthorizedException {
        return imageService.deleteAdvertisementPicture(id);
    }
}
