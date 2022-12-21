package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.RoomModel;
import com.manusmd.mystudyv2.repository.RoomRepository;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.throwable.ResourceExists;
import com.manusmd.mystudyv2.throwable.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    RoomRepository roomRepository;

    public CustomResponse createRoom(RoomModel room) {
        try {
            RoomModel.canCreate(room, roomRepository);
            RoomModel newRoom = roomRepository.save(room);
            return CustomResponse.CREATED(newRoom, "Room");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceExists e) {
            return CustomResponse.ALREADY_EXISTS(e.getResource(), e.getResourceName(), e.getCheckedProperty());
        }
    }

    public CustomResponse getRoom(String id) {
        try {
            RoomModel foundRoom = RoomModel.roomExistsById(id, roomRepository);
            return CustomResponse.FOUND(foundRoom, "Room");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getId());
        }
    }

    public CustomResponse getAllRooms() {
        try {
            List<RoomModel> foundRooms = roomRepository.findAll();
            return CustomResponse.FOUND_FETCHED_LIST(foundRooms, "Room");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse updateRoom(String id, RoomModel room) {
        try {
            RoomModel foundRoom = RoomModel.roomExistsById(id, roomRepository);
            if (!foundRoom.getName().equals(room.getName())) {
                RoomModel.checkNameChangeLegit(room, roomRepository);
            }
            if (room.getName() != null) foundRoom.setName(room.getName());
            if (room.getCapacity() != null) foundRoom.setCapacity(room.getCapacity());
            if (room.getNotes() != null) foundRoom.setNotes(room.getNotes());
            RoomModel updatedRoom = roomRepository.save(foundRoom);
            return CustomResponse.OK_PUT(updatedRoom, "Room");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getId());
        } catch (ResourceExists e) {
            return CustomResponse.ALREADY_EXISTS(e.getResource(), e.getResourceName(), e.getCheckedProperty());
        }
    }

    public CustomResponse deleteRoom(String id) {
        try {
            RoomModel foundRoom = RoomModel.roomExistsById(id, roomRepository);
            roomRepository.deleteById(id);
            return CustomResponse.OK_DELETE("Room", foundRoom.getId());
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getId());
        }
    }
}
