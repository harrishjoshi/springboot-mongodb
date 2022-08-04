package com.harxsh.springboot.mongodb.controller;

import com.harxsh.springboot.mongodb.collection.ProfileImage;
import com.harxsh.springboot.mongodb.service.ProfileImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("profile-image")
@RequiredArgsConstructor
public class ProfileImageController {

    private final ProfileImageService profileImageService;

    @PostMapping("upload")
    public String upload(@RequestParam("image") MultipartFile file) throws IOException {
        return profileImageService.uploadImage(file.getOriginalFilename(), file);
    }

    @GetMapping("download/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String id) {
        ProfileImage image = profileImageService.getProfileImage(id);
        Resource resource = new ByteArrayResource(image.getImage().getData());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getTitle() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
