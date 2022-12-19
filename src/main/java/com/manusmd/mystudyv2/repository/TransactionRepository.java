package com.manusmd.mystudyv2.repository;

import com.manusmd.mystudyv2.model.TransactionModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<TransactionModel, String> {
}
