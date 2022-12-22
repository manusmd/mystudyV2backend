package com.manusmd.mystudyv2.repository;

import com.manusmd.mystudyv2.model.AuthUserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthUserRepository extends MongoRepository<AuthUserModel, String> {
    Optional<AuthUserModel> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
