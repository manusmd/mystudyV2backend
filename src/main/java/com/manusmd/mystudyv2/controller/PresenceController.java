package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.PresenceModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.service.PresenceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PresenceController {
    PresenceService presenceService;

    @PostMapping("/Presences")
    public ResponseEntity<CustomResponse> createPresence(@ModelAttribute PresenceModel presence){
        CustomResponse response = presenceService.createPresence(presence);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/Presences/{id}")
    public ResponseEntity<CustomResponse> getPresence(@PathVariable String id){
        CustomResponse response = presenceService.getPresence(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/Students/{studentId}/Presences")
    public ResponseEntity<CustomResponse> getPresencesByStudent(@PathVariable String studentId){
        CustomResponse response = presenceService.getPresencesByStudent(studentId);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/Presences")
    public ResponseEntity<CustomResponse> getPresences(){
        CustomResponse response = presenceService.getPresences();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/Presences/{id}")
    public ResponseEntity<CustomResponse> updatePresence(@ModelAttribute PresenceModel presence, @PathVariable String id){
        CustomResponse response = presenceService.updatePresence(id, presence);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/Presences/{id}")
    public ResponseEntity<CustomResponse> deletePresence(@PathVariable String id){
        CustomResponse response = presenceService.deletePresence(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
