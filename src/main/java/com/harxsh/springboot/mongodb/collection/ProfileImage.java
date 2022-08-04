package com.harxsh.springboot.mongodb.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "profile_image")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProfileImage {

    @Id
    private String id;
    private String title;
    private Binary image;
}
