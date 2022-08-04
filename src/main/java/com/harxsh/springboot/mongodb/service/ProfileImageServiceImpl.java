package com.harxsh.springboot.mongodb.service;

import com.harxsh.springboot.mongodb.collection.ProfileImage;
import com.harxsh.springboot.mongodb.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileImageServiceImpl implements ProfileImageService {

    private final ProfileImageRepository profileImageRepository;

    @Override
    public String uploadImage(String originalFilename, MultipartFile file) throws IOException {
        ProfileImage profileImage = new ProfileImage();
        profileImage.setTitle(originalFilename);
        profileImage.setImage(new Binary(BsonBinarySubType.BINARY, file .getBytes()));
        return profileImageRepository.save(profileImage).getId();
    }

    @Override
    public ProfileImage getProfileImage(String id) {
        return profileImageRepository.findById(id).get();
    }
}
