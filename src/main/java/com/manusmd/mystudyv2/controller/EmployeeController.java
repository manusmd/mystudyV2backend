package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.EmployeeModel;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class EmployeeController {
    EmployeeService employeeService;
    @PostMapping("/Employees")
    public ResponseEntity<CustomResponse> createEmployee(@ModelAttribute EmployeeModel employee){
        CustomResponse response = employeeService.createTeacher(employee);
        return new ResponseEntity<>(response, response.getStatus());
    }
    @GetMapping("/Employees/{id}")
    public ResponseEntity<CustomResponse> getEmployee(@PathVariable String id){
        CustomResponse response = employeeService.getEmployee(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @GetMapping("/Employees")
    public ResponseEntity<CustomResponse> getAllEmployees(){
        CustomResponse response = employeeService.getAllEmployees();
        return new ResponseEntity<>(response,response.getStatus());
    }
    @PutMapping("/Employees/{id}")
    public ResponseEntity<CustomResponse> updateEmployee(@ModelAttribute EmployeeModel employee,
                                                                        @PathVariable String id){
        CustomResponse response = employeeService.updateEmployee(employee, id);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @PutMapping("/Employees/{id}/active")
    public ResponseEntity<CustomResponse> toggleEmployeeStatus(@PathVariable String id){
        CustomResponse response = employeeService.toggleStatus(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
    @DeleteMapping("/Employees/{id}")
    public ResponseEntity<CustomResponse> deleteEmployee(@PathVariable String id){
        CustomResponse response = employeeService.deleteEmployee(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
}
