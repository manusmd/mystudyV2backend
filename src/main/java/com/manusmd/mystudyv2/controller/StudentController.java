package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.StudentModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class StudentController {
    StudentService studentService;
    @PostMapping("/Students")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER')")
    public ResponseEntity<CustomResponse> createStudent(@ModelAttribute StudentModel student) {
        CustomResponse response = studentService.createStudent(student);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @GetMapping("/Students/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER') or hasRole('STUDENT')")
    public ResponseEntity<CustomResponse> getStudent(@PathVariable String id) {
        CustomResponse response = studentService.getStudent(id);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @GetMapping("/Students")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER')")
    public ResponseEntity<CustomResponse> getAllStudents(){
        CustomResponse response = studentService.getAllStudents();
        return new ResponseEntity<>(response,response.getStatus());
    }
    @PutMapping("/Students/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER') or hasRole('STUDENT')")
    public ResponseEntity<CustomResponse> updateStudent(@ModelAttribute StudentModel student,
                                                                      @PathVariable String id){
        CustomResponse response = studentService.updateStudent(student, id);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @PutMapping("/Students/{id}/active")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER')")
    public ResponseEntity<CustomResponse> toggleStudentStatus(@PathVariable String id){
        CustomResponse response = studentService.toggleStatus(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @DeleteMapping("/Students/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('TEACHER')")
    public ResponseEntity<CustomResponse> deleteStudent(@PathVariable String id){
        CustomResponse response = studentService.deleteStudent(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
}
