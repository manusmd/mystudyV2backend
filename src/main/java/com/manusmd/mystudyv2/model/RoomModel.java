package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.RoomRepository;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Data
@Document("rooms")
public class RoomModel {
    @Id
    private String id;
    private String name;
    private Integer capacity;
    private String notes;

    public boolean checkNameChangeLegit(RoomModel room, RoomRepository roomRepository){
        Optional<RoomModel> foundRoom = roomRepository.findByName(room.getName());
        return foundRoom.isPresent();
    }
}
