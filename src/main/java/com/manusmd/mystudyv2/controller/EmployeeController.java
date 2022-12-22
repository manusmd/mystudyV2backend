package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.EmployeeModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class EmployeeController {
    EmployeeService employeeService;
    @PostMapping("/Employees")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomResponse> createEmployee(@ModelAttribute EmployeeModel employee){
        CustomResponse response = employeeService.createTeacher(employee);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @GetMapping("/Employees/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<CustomResponse> getEmployee(@PathVariable String id){
        CustomResponse response = employeeService.getEmployee(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @GetMapping("/Employees")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomResponse> getAllEmployees(){
        CustomResponse response = employeeService.getAllEmployees();
        return new ResponseEntity<>(response,response.getStatus());
    }
    @PutMapping("/Employees/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<CustomResponse> updateEmployee(@ModelAttribute EmployeeModel employee,
                                                                        @PathVariable String id){
        CustomResponse response = employeeService.updateEmployee(employee, id);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @PutMapping("/Employees/{id}/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomResponse> toggleEmployeeStatus(@PathVariable String id){
        CustomResponse response = employeeService.toggleStatus(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @DeleteMapping("/Employees/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<CustomResponse> deleteEmployee(@PathVariable String id){
        CustomResponse response = employeeService.deleteEmployee(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
}
