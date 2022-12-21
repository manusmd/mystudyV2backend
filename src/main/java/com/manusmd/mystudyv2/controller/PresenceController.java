package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.PresenceModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.service.PresenceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PresenceController {
    PresenceService presenceService;

    @PostMapping("/Presences")
    public ResponseEntity<CustomResponse> createPresence(@ModelAttribute PresenceModel presence){
        CustomResponse response = presenceService.createPresence(presence);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
