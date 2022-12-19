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
    public ResponseEntity<CustomResponse> createStudent(@ModelAttribute StudentModel student) {
        CustomResponse response = studentService.createStudent(student);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @GetMapping("/Students/{id}")
    public ResponseEntity<CustomResponse> getStudent(@PathVariable String id) {
        CustomResponse response = studentService.getStudent(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @GetMapping("/Students")
    public ResponseEntity<CustomResponse> getAllStudents(){
        CustomResponse response = studentService.getAllStudents();
        return new ResponseEntity<>(response,response.getStatus());
    }
    @PutMapping("/Students/{id}")
    public ResponseEntity<CustomResponse> updateStudent(@ModelAttribute StudentModel student,
                                                                      @PathVariable String id){
        CustomResponse response = studentService.updateStudent(student, id);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @PutMapping("/Students/{id}/active")
    public ResponseEntity<CustomResponse> toggleStudentStatus(@PathVariable String id){
        CustomResponse response = studentService.toggleStatus(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @DeleteMapping("/Students/{id}")
    public ResponseEntity<CustomResponse> deleteStudent(@PathVariable String id){
        CustomResponse response = studentService.deleteStudent(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
}
