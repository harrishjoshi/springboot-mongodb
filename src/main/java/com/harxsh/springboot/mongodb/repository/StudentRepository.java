package com.harxsh.springboot.mongodb.repository;

import com.harxsh.springboot.mongodb.collection.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    @Query("{$or: [{firstName: ?0}, {lastName: ?0}]}")
    List<Student> getStudentByFirstNameOrLastName(String name);

    List<Student> findByFirstNameStartsWith(String name);

    List<Student> findByAgeBetween(Integer min, Integer max);

    @Query(value = "{'age' : {$gte: ?0, $lte: ?1}}", fields = "{addresses : 0, subjects : 0}")
    List<Student> findStudentByAgeBetween(Integer min, Integer max);
}
