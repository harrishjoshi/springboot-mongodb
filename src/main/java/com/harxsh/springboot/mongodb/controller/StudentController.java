package com.harxsh.springboot.mongodb.controller;

import com.harxsh.springboot.mongodb.collection.Student;
import com.harxsh.springboot.mongodb.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("add")
    public String saveStudent(@RequestBody Student student) {
        return studentService.save(student);
    }

    @GetMapping("find-by-name")
    public List<Student> getStudentByFirstNameOrLastName(@RequestParam String name) {
        return studentService.getStudentByFirstNameOrLastName(name);
    }

    @GetMapping("find-start-with")
    public List<Student> getStudentStartWith(@RequestParam String name) {
        return studentService.getStudentStartWith(name);
    }

    @DeleteMapping("delete/{id}")
    public void deleteStudent(@PathVariable String id) {
        studentService.delete(id);
    }

    @GetMapping("age")
    public List<Student> getByStudentAge(@RequestParam Integer minAge, @RequestParam Integer maxAge) {
        return studentService.getByStudentAge(minAge, maxAge);
    }

    @GetMapping("search")
    public Page<Student> search(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return studentService.search(firstName, lastName, minAge, maxAge, city, pageable);
    }

    @GetMapping("youngest-student")
    public List<Document> getYoungestStudent() {
        return studentService.getYoungestStudentByCity();
    }

    @GetMapping("student-by-city")
    public List<Document> getStudentByCity() {
        return studentService.getStudentByCity();
    }
}
