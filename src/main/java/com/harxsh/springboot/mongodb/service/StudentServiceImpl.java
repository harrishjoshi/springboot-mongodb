package com.harxsh.springboot.mongodb.service;

import com.harxsh.springboot.mongodb.collection.Student;
import com.harxsh.springboot.mongodb.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public String save(Student student) {
        return studentRepository.save(student).getStudentId();
    }

    @Override
    public List<Student> getStudentByFirstNameOrLastName(String name) {
        return studentRepository.getStudentByFirstNameOrLastName(name);
    }

    @Override
    public void delete(String id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudentStartWith(String name) {
        return studentRepository.findByFirstNameStartsWith(name);
    }

    @Override
    public List<Student> getByStudentAge(Integer min, Integer max) {
        return studentRepository.findStudentByAgeBetween(min, max);
    }

    @Override
    public Page<Student> search(String firstName, String lastName, Integer minAge, Integer maxAge, String city, Pageable pageable) {
        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();

        if (isNotEmpty(firstName))
            criteria.add(Criteria.where("firstName").regex(firstName, "i"));

        if (isNotEmpty(lastName))
            criteria.add(Criteria.where("lastName").regex(lastName, "i"));

        if (minAge != null)
            criteria.add(Criteria.where("age").gte(minAge));

        if (maxAge != null)
            criteria.add(Criteria.where("age").lte(maxAge));

        if (isNotEmpty(city))
            criteria.add(Criteria.where("addresses.city").is(city));

        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Student.class), pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), Student.class)
        );
    }

    private boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
