package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.RoomModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class RoomController {
    RoomService roomService;

    @PostMapping("/Rooms")
    public ResponseEntity<CustomResponse> createRoom(@ModelAttribute RoomModel room){
        CustomResponse response = roomService.createRoom(room);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @GetMapping("/Rooms/{id}")
    public ResponseEntity<CustomResponse> getRoom(@PathVariable String id){
        CustomResponse response = roomService.getRoom(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
}
