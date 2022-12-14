package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.PresenceModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.service.PresenceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class PresenceController {
    PresenceService presenceService;

    @PostMapping("/Presences")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER')")
    public ResponseEntity<CustomResponse> createPresence(@ModelAttribute PresenceModel presence){
        CustomResponse response = presenceService.createPresence(presence);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/Presences/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER') or hasRole('STUDENT')")
    public ResponseEntity<CustomResponse> getPresence(@PathVariable String id){
        CustomResponse response = presenceService.getPresence(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/Students/{studentId}/Presences")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER') or hasRole('STUDENT')")
    public ResponseEntity<CustomResponse> getPresencesByStudent(@PathVariable String studentId){
        CustomResponse response = presenceService.getPresencesByStudent(studentId);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/Presences")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER')")
    public ResponseEntity<CustomResponse> getPresences(){
        CustomResponse response = presenceService.getPresences();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/Presences/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER')")
    public ResponseEntity<CustomResponse> updatePresence(@ModelAttribute PresenceModel presence, @PathVariable String id){
        CustomResponse response = presenceService.updatePresence(id, presence);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/Presences/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER')")
    public ResponseEntity<CustomResponse> deletePresence(@PathVariable String id){
        CustomResponse response = presenceService.deletePresence(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
