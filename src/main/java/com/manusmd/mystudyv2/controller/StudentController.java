package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.StudentModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class StudentController {
    StudentService studentService;

    @PostMapping("/Students")
    public ResponseEntity<CustomResponse<StudentModel>> createStudent(@ModelAttribute StudentModel student) {
        CustomResponse<StudentModel> response = studentService.createStudent(student);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/Students/{id}")
    public ResponseEntity<CustomResponse<StudentModel>> getStudent(@PathVariable String id) {
        CustomResponse<StudentModel> response = studentService.getStudent(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
