package com.harxsh.springboot.mongodb.service;

import com.harxsh.springboot.mongodb.StringUtil;
import com.harxsh.springboot.mongodb.collection.Student;
import com.harxsh.springboot.mongodb.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
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
    public Page<Student> search(
            String firstName, String lastName, Integer minAge,
            Integer maxAge, String city, Pageable pageable
    ) {
        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();

        if (StringUtil.isNotEmpty(firstName))
            criteria.add(Criteria.where("firstName").regex(firstName, "i"));

        if (StringUtil.isNotEmpty(lastName))
            criteria.add(Criteria.where("lastName").regex(lastName, "i"));

        if (minAge != null)
            criteria.add(Criteria.where("age").gte(minAge));

        if (maxAge != null)
            criteria.add(Criteria.where("age").lte(maxAge));

        if (StringUtil.isNotEmpty(city))
            criteria.add(Criteria.where("addresses.city").is(city));

        if (!criteria.isEmpty())
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Student.class), pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), Student.class)
        );
    }

    @Override
    public List<Document> getYoungestStudentByCity() {
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.ASC, "age");
        GroupOperation groupOperation = Aggregation.group("addresses.city").first(Aggregation.ROOT)
                .as("youngestStudent");
        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, sortOperation, groupOperation);

        return mongoTemplate
                .aggregate(aggregation, Student.class, Document.class)
                .getMappedResults();
    }

    @Override
    public List<Document> getStudentByCity() {
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        GroupOperation groupOperation = Aggregation.group("addresses.city").count().as("studentCount");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.ASC, "studentCount");
        ProjectionOperation projectionOperation = Aggregation.project().andExpression("_id").as("city")
                .andExpression("studentCount").as("count")
                .andExclude("_id");
        Aggregation aggregation = Aggregation.newAggregation(
                unwindOperation, groupOperation, sortOperation, projectionOperation
        );

        return mongoTemplate
                .aggregate(aggregation, Student.class, Document.class)
                .getMappedResults();
    }
}
