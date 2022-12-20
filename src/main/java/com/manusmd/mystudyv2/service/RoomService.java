package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.RoomModel;
import com.manusmd.mystudyv2.repository.RoomRepository;
import com.manusmd.mystudyv2.response.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomService {
    RoomRepository roomRepository;
    public CustomResponse createRoom(RoomModel room) {
        try {
            Optional<RoomModel> foundRoom = roomRepository.findByName(room.getName());
            if(foundRoom.isPresent()){
                return CustomResponse.ALREADY_EXISTS(room, "Room", "name");
            }
            RoomModel newRoom = roomRepository.save(room);
            return CustomResponse.CREATED(newRoom, "Room");
        } catch (Exception e){
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse getRoom(String id) {
        try {
            Optional<RoomModel> foundRoom = roomRepository.findById(id);
            if(foundRoom.isEmpty()){
                return CustomResponse.NOT_FOUND("Room", id);
            }
            return CustomResponse.FOUND(foundRoom.get(),"Room");
        } catch (Exception e){
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }
}
