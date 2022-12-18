package com.manusmd.mystudyv2.repository;

import com.manusmd.mystudyv2.model.StudentModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<StudentModel, String> {
    Optional<StudentModel> findByEmail(String email);

}

