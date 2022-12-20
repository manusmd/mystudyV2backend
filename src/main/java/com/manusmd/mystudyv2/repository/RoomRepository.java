package com.manusmd.mystudyv2.repository;

import com.manusmd.mystudyv2.model.RoomModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoomRepository extends MongoRepository<RoomModel, String> {
    Optional<RoomModel> findByName(String name);
}
