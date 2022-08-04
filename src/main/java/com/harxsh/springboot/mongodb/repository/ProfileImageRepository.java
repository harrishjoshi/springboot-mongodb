package com.harxsh.springboot.mongodb.repository;

import com.harxsh.springboot.mongodb.collection.ProfileImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileImageRepository extends MongoRepository<ProfileImage, String> {
}
