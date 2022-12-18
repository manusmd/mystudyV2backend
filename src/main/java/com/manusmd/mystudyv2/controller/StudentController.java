package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.StudentModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/Students")
    public ResponseEntity<CustomResponse<List<StudentModel>>> getAllStudents(){
        CustomResponse<List<StudentModel>> response = studentService.getAllStudents();
        return new ResponseEntity<>(response,response.getStatus());
    }

    @PutMapping("/Students/{id}")
    public ResponseEntity<CustomResponse<StudentModel>> updateStudent(@ModelAttribute StudentModel student,
                                                                      @PathVariable String id){
        CustomResponse<StudentModel> response = studentService.updateStudent(student, id);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @PutMapping("/Students/{id}/active")
    public ResponseEntity<CustomResponse<StudentModel>> toggleStudentStatus(@PathVariable String id){
        CustomResponse<StudentModel> response = studentService.toggleStatus(id);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @DeleteMapping("/Students/{id}")
    public ResponseEntity<CustomResponse<StudentModel>> deleteStudent(@PathVariable String id){
        CustomResponse<StudentModel> response = studentService.deleteStudent(id);
        return new ResponseEntity<>(response,response.getStatus());

    }
}
