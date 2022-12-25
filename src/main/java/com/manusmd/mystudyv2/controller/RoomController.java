package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.RoomModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class RoomController {
    RoomService roomService;

    @PostMapping("/Rooms")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<CustomResponse> createRoom(@ModelAttribute RoomModel room) {
        CustomResponse response = roomService.createRoom(room);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/Rooms/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER') or hasRole('STUDENT')")
    public ResponseEntity<CustomResponse> getRoom(@PathVariable String id) {
        CustomResponse response = roomService.getRoom(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/Rooms")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER')")
    public ResponseEntity<CustomResponse> getAllRooms() {
        CustomResponse response = roomService.getAllRooms();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/Rooms/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER') or hasRole('STUDENT')")
    public ResponseEntity<CustomResponse> updateRoom(@ModelAttribute RoomModel room, @PathVariable String id) {
        CustomResponse response = roomService.updateRoom(id, room);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/Rooms/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<CustomResponse> deleteRoom(@PathVariable String id) {
        CustomResponse response = roomService.deleteRoom(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
