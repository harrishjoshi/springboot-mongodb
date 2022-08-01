package com.harxsh.springboot.mongodb.collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "student")
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    private String studentId;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int age;
    private List<String> subjects;
    private List<Address> addresses;
}
