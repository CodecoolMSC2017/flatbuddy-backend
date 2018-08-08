package com.codecool.flatbuddy.service;

import com.codecool.flatbuddy.exception.InvalidUploadTypeException;
import com.codecool.flatbuddy.exception.UnauthorizedException;
import com.codecool.flatbuddy.model.AdPicture;
import com.codecool.flatbuddy.model.User;
import com.codecool.flatbuddy.model.UserPicture;
import com.codecool.flatbuddy.repository.AdPictureRepository;
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
    @Autowired
    private AdPictureRepository adPictureRepository;
    @Autowired
    private AdvertisementService advertisementService;

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

    public void rentadPictreUpload(MultipartFile file, int rentAdId) throws IOException, InvalidUploadTypeException, UnauthorizedException {
        User loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Path directory = Paths.get(path + "/advertisements/" + loggedUser.getId());
        String fullPath = path + "/advertisements/" + loggedUser.getId();
        if(!advertisementService.isAdvertisementMine(rentAdId)){
            throw new UnauthorizedException("This advertisement is not yours");
        }


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

        AdPicture adPicture = new AdPicture();
        adPicture.setAdId(rentAdId);
        adPicture.setPath(uploadedFile.getName());
        adPictureRepository.save(adPicture);
    }

    public void deleteProfilePicture(int id) throws UnauthorizedException {
        User loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        UserPicture userPicture = userPictureRepository.findById(id).get();

        if(!userPicture.getUserId().equals(loggedUser.getId())){
            throw new UnauthorizedException("Not allowed to delete other's picture");
        }
        userPictureRepository.delete(userPicture);
    }

    public void deleteAdvertisementPicture(int id) throws UnauthorizedException {
        User loggedUser = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        AdPicture adPicture = adPictureRepository.findById(id).get();

        if(!advertisementService.isAdvertisementMine(loggedUser.getId())){
            throw new UnauthorizedException("Not allowed to delete other's picture");
        }
        adPictureRepository.delete(adPicture);

    }
}
