package com.manusmd.mystudyv2.repository;

import com.manusmd.mystudyv2.model.TeacherModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeacherRepository extends MongoRepository<TeacherModel, String> {
    TeacherModel findByEmail(String email);

}

