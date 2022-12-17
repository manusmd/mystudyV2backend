package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.model.TeacherModel;
import com.manusmd.mystudyv2.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TeacherController {

    TeacherService teacherService;

    @PostMapping("/Teachers")
    public ResponseEntity<CustomResponse<TeacherModel>> createTeacher(@ModelAttribute TeacherModel teacher) {
        CustomResponse<TeacherModel> response = teacherService.createTeacher(teacher);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/Teachers/{id}")
    public ResponseEntity<CustomResponse<TeacherModel>> getTeacher(@PathVariable String id) {
        CustomResponse<TeacherModel> response = teacherService.getTeacher(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/Teachers")
    public ResponseEntity<CustomResponse<List<TeacherModel>>> getAllTeachers() {
        CustomResponse<List<TeacherModel>> response = teacherService.getAllTeachers();
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/Teachers/{id}")
    public ResponseEntity<CustomResponse<TeacherModel>> updateTeacher(@PathVariable String id, @ModelAttribute TeacherModel teacher) {
        CustomResponse<TeacherModel> response = teacherService.updateTeacher(id, teacher);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PutMapping("/Teachers/{id}/active")
    public ResponseEntity<CustomResponse<TeacherModel>> toggleTeacherStatus(@PathVariable String id) {
        CustomResponse<TeacherModel> response = teacherService.toggleStatus(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @DeleteMapping("/Teachers/{id}")
    public ResponseEntity<CustomResponse<TeacherModel>> deleteTeacher(@PathVariable String id){
        CustomResponse<TeacherModel> response = teacherService.deleteTeacher(id);
        return new ResponseEntity<>(response, response.getStatus());
    }

}


