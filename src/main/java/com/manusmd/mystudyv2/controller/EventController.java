package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.EventModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class EventController {
    EventService eventService;

    @PostMapping("/Events")
    public ResponseEntity<CustomResponse> createEvent(@ModelAttribute EventModel event){
        CustomResponse response = eventService.createEvent(event);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @GetMapping("/Events/{id}")
    public ResponseEntity<CustomResponse> getEvent(@PathVariable String id){
        CustomResponse response = eventService.getEvent(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
}
