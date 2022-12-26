package com.manusmd.mystudyv2.repository;

import com.manusmd.mystudyv2.model.TeacherModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TeacherRepository extends MongoRepository<TeacherModel,String> {
    Optional<TeacherModel> findByEmail(String email);


    Optional<TeacherModel> findByUsername(String username);
}

