package com.harxsh.springboot.mongodb.service;

import com.harxsh.springboot.mongodb.collection.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    String save(Student student);

    List<Student> getStudentByFirstNameOrLastName(String name);

    void delete(String id);

    List<Student> getStudentStartWith(String name);

    List<Student> getByStudentAge(Integer minAge, Integer maxAge);

    Page<Student> search(String firstName, String lastName, Integer minAge, Integer maxAge, String city, Pageable pageable);
}
