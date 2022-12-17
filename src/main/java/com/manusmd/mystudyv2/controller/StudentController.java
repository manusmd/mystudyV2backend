package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.StudentModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StudentController {
    StudentService studentService;

    @PostMapping("/Students")
    public ResponseEntity<CustomResponse<StudentModel>> createStudent(@ModelAttribute StudentModel student) {
        CustomResponse<StudentModel> response = studentService.createStudent(student);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
