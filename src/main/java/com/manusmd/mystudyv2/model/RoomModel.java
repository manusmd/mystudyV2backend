package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.EventRepository;
import com.manusmd.mystudyv2.repository.RoomRepository;
import com.manusmd.mystudyv2.throwable.ResourceConflict;
import com.manusmd.mystudyv2.throwable.ResourceExists;
import com.manusmd.mystudyv2.throwable.ResourceNotFound;
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

    public static void checkNameChangeLegit(RoomModel room, RoomRepository roomRepository) throws ResourceExists {
        Optional<RoomModel> foundRoom = roomRepository.findByName(room.getName());
        if(foundRoom.isPresent()){
            throw new ResourceExists(room, "Room", "name");
        }
    }

    public static void isRoomAvailable(EventModel event, RoomRepository roomRepository, EventRepository eventRepository) throws ResourceConflict, ResourceNotFound {
        Optional<RoomModel> foundRoom = roomRepository.findById(event.getRoom());
        if (foundRoom.isEmpty()) {
            throw new ResourceNotFound("Room " , event.getRoom());
        }
        if (foundRoom.get().getCapacity() < event.getStudents().size()) {
            throw new ResourceConflict(foundRoom.get().name + " is not big enough");
        }
        if (!event.checkDateTimeLegit(eventRepository.findAllByRoom(event.getRoom()))) {
            throw new ResourceConflict(foundRoom.get().name + " is not available at this time");
        }
    }

    public static void canCreate(RoomModel room, RoomRepository roomRepository) throws ResourceExists {
        Optional<RoomModel> foundRoom = roomRepository.findByName(room.getName());
        if (foundRoom.isPresent()) {
            throw new ResourceExists(room, "Room ", "name");
        }
    }

    public static RoomModel roomExistsById(String id, RoomRepository roomRepository) throws ResourceNotFound {
        Optional<RoomModel> foundRoom = roomRepository.findById(id);
        if (foundRoom.isEmpty()) {
            throw new ResourceNotFound("Room", id);
        }
        return foundRoom.get();
    }
}
