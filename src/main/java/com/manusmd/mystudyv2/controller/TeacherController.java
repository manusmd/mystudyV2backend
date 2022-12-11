package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.CustomResponse;
import com.manusmd.mystudyv2.model.TeacherModel;
import com.manusmd.mystudyv2.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TeacherController {

    TeacherService teacherService;

    @PostMapping("/teachers")
    public ResponseEntity<CustomResponse> createTeacher(@RequestBody TeacherModel teacher){
        CustomResponse<TeacherModel> response = teacherService.createTeacher(teacher);
        return new ResponseEntity<>(response, response.getStatus());
    }
}


