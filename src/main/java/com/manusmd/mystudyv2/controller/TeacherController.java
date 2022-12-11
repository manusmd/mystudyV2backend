package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.CustomResponse;
import com.manusmd.mystudyv2.model.TeacherModel;
import com.manusmd.mystudyv2.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TeacherController {

    TeacherService teacherService;

    @PostMapping("/teachers")
    public ResponseEntity<CustomResponse> createTeacher(@ModelAttribute TeacherModel teacher) {
        CustomResponse<TeacherModel> response = teacherService.createTeacher(teacher);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<CustomResponse> getTeacher(@PathVariable String id) {
        CustomResponse<TeacherModel> response = teacherService.getTeacher(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}


