package com.manusmd.mystudyv2.repository;

import com.manusmd.mystudyv2.model.EmployeeModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<EmployeeModel, String> {
    Optional<EmployeeModel> findByEmail(String email);

}
