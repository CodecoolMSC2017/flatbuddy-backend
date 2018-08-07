package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.exception.InvalidUploadTypeException;
import com.codecool.flatbuddy.exception.UnauthorizedException;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.model.UserPicture;
import com.codecool.flatbuddy.repository.UserPictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ImageService {

    @Value("${upload.path}")
    private String path;
    @Autowired
    private UserService userService;
    @Autowired
    private UserPictureRepository userPictureRepository;

    public void profilePictureUpload(MultipartFile file) throws IOException, InvalidUploadTypeException {
        User loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Path directory = Paths.get(path + "/profiles/" + loggedUser.getId());
        String fullPath = path + "/profiles/" + loggedUser.getId();

        if(file == null){
            throw new InvalidUploadTypeException();
        }
        if (!file.getContentType().equals("image/jpeg")) {
            throw new InvalidUploadTypeException("Only jpg allowed!");
        }

        if (!Files.exists(directory)) {
            new File(fullPath).mkdirs();
        }

        File uploadedFile = new File(fullPath, file.getOriginalFilename());
        uploadedFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(uploadedFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();

        UserPicture userPicture = new UserPicture();
        userPicture.setUserId(loggedUser.getId());
        userPicture.setPath(uploadedFile.getName());
        userPictureRepository.save(userPicture);
    }

    public void rentadPictreUpload(MultipartFile[] files) throws IOException {
        User loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        FileOutputStream fileOutputStream = null;
        Path directory = Paths.get(path + "/advertisements/" + loggedUser.getId());
        String fullPath = path + "/advertisements/" + loggedUser.getId();

        if (!Files.exists(directory)) {
            new File(fullPath).mkdirs();
        }

        for (MultipartFile multipartFile : files) {
            File uploadedFile = new File(fullPath, multipartFile.getOriginalFilename());
            uploadedFile.createNewFile();
            fileOutputStream = new FileOutputStream(uploadedFile);
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();
        }
    }

    public void deleteProfilePicture(int id) throws UnauthorizedException {
        User loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        UserPicture userPicture = userPictureRepository.findById(id).get();

        if(!userPicture.getUserId().equals(loggedUser.getId())){
            throw new UnauthorizedException("Not allowed to delete other's picture");
        }
        userPictureRepository.delete(userPicture);
    }
}
