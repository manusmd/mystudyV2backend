package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.CustomResponse;
import com.manusmd.mystudyv2.model.EmployeeModel;
import com.manusmd.mystudyv2.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class EmployeeController {
    EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<CustomResponse<EmployeeModel>> createEmployee(@ModelAttribute EmployeeModel employee){
        CustomResponse<EmployeeModel> response = employeeService.createTeacher(employee);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<CustomResponse<EmployeeModel>> getEmployee(@PathVariable String id){
        CustomResponse<EmployeeModel> response = employeeService.getEmployee(id);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @GetMapping("/employees")
    public ResponseEntity<CustomResponse<List<EmployeeModel>>> getAllEmployees(){
        CustomResponse<List<EmployeeModel>> response = employeeService.getAllEmployees();
        return new ResponseEntity<>(response,response.getStatus());
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<CustomResponse<EmployeeModel>> updateEmployee(@ModelAttribute EmployeeModel employee,
                                                                        @PathVariable String id){
        CustomResponse<EmployeeModel> response = employeeService.updateEmployee(employee, id);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @PutMapping("/employees/{id}/active")
    public ResponseEntity<CustomResponse<EmployeeModel>> toggleEmployeeStatus(@PathVariable String id){
        CustomResponse<EmployeeModel> response = employeeService.toggleStatus(id);
        return new ResponseEntity<>(response,response.getStatus());
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<CustomResponse<EmployeeModel>> deleteEmployee(@PathVariable String id){
        CustomResponse<EmployeeModel> response = employeeService.deleteEmployee(id);
        return new ResponseEntity<>(response,response.getStatus());
    }
}
