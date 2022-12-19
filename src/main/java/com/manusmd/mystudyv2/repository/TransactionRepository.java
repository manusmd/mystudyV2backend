package com.manusmd.mystudyv2.repository;

import com.manusmd.mystudyv2.model.TransactionModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<TransactionModel, String> {
    public List<TransactionModel> findByStudentId(String studentId);
}
