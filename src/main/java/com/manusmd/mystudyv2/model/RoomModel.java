package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.EventRepository;
import com.manusmd.mystudyv2.repository.RoomRepository;
import com.manusmd.mystudyv2.throwable.ResourceConflict;
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

    public static void isRoomAvailable(EventModel event, RoomRepository roomRepository, EventRepository eventRepository) throws ResourceConflict {
        Optional<RoomModel> foundRoom = roomRepository.findById(event.getRoom());
        if(foundRoom.isEmpty()){
            throw new ResourceConflict("Room " + event.getRoom() + " not found");
        }
        if(foundRoom.get().getCapacity() < event.getStudents().size()){
            throw new ResourceConflict(foundRoom.get().name + " is not big enough");
        }
        if(!event.checkDateTimeLegit(eventRepository.findAllByRoom(event.getRoom()))){
            throw new ResourceConflict(foundRoom.get().name + " is not available at this time");
        }
    }
}
