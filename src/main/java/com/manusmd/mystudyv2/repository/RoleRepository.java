package com.manusmd.mystudyv2.repository;

import com.manusmd.mystudyv2.model.ERole;
import com.manusmd.mystudyv2.model.RoleModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<RoleModel, String> {
    Optional<RoleModel> findByName(ERole name);
}
