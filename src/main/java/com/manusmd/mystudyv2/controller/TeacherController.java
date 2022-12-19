package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.TeacherModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class TeacherController {
    TeacherService teacherService;
    @PostMapping("/Teachers")
    public ResponseEntity<CustomResponse> createTeacher(@ModelAttribute TeacherModel teacher) {
        CustomResponse response = teacherService.createTeacher(teacher);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @GetMapping("/Teachers/{id}")
    public ResponseEntity<CustomResponse> getTeacher(@PathVariable String id) {
        CustomResponse response = teacherService.getTeacher(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @GetMapping("/Teachers")
    public ResponseEntity<CustomResponse> getAllTeachers() {
        CustomResponse response = teacherService.getAllTeachers();
        return new ResponseEntity<>(response, response.getStatus());
    }
    @PutMapping("/Teachers/{id}")
    public ResponseEntity<CustomResponse> updateTeacher(@PathVariable String id, @ModelAttribute TeacherModel teacher) {
        CustomResponse response = teacherService.updateTeacher(id, teacher);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @PutMapping("/Teachers/{id}/active")
    public ResponseEntity<CustomResponse> toggleTeacherStatus(@PathVariable String id) {
        CustomResponse response = teacherService.toggleStatus(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @DeleteMapping("/Teachers/{id}")
    public ResponseEntity<CustomResponse> deleteTeacher(@PathVariable String id){
        CustomResponse response = teacherService.deleteTeacher(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}


