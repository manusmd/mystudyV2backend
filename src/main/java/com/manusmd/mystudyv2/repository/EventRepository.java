package com.manusmd.mystudyv2.repository;

import com.manusmd.mystudyv2.model.EventModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
public interface EventRepository extends MongoRepository<EventModel, String> {
    List<EventModel> findByStudentsContains(String id);
    List<EventModel> findAllByTeacher(String id);
    List<EventModel> findAllByRoom(String room);
}
