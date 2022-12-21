package com.manusmd.mystudyv2.repository;

import com.manusmd.mystudyv2.model.PresenceModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PresenceRepository extends MongoRepository<PresenceModel,String> {
    Optional<PresenceModel> findByStudentIdAndEventId(String studentId, String eventId);
}
