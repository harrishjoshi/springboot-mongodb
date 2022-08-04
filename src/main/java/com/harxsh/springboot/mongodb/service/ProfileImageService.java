package com.harxsh.springboot.mongodb.service;

import com.harxsh.springboot.mongodb.collection.ProfileImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfileImageService {

    String uploadImage(String originalFilename, MultipartFile file) throws IOException;

    ProfileImage getProfileImage(String id);
}
