package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.EventModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class EventController {
    EventService eventService;

    @PostMapping("/Events")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER')")
    public ResponseEntity<CustomResponse> createEvent(@ModelAttribute EventModel event) {
        CustomResponse response = eventService.createEvent(event);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/Events/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER') or hasRole('STUDENT')")
    public ResponseEntity<CustomResponse> getEvent(@PathVariable String id) {
        CustomResponse response = eventService.getEvent(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/Events")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER') or hasRole('STUDENT')")
    public ResponseEntity<CustomResponse> getEvents() {
        CustomResponse response = eventService.getEvents();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/Events/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER')")
    public ResponseEntity<CustomResponse> updateEvent(@ModelAttribute EventModel event, @PathVariable String id) {
        CustomResponse response = eventService.updateEvent(id, event);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/Events/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER')")
    public ResponseEntity<CustomResponse> deleteEvent(@PathVariable String id) {
        CustomResponse response = eventService.deleteEvent(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
