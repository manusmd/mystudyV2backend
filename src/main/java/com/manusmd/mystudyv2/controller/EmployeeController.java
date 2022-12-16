package com.manusmd.mystudyv2.controller;

import com.manusmd.mystudyv2.model.CustomResponse;
import com.manusmd.mystudyv2.model.EmployeeModel;
import com.manusmd.mystudyv2.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EmployeeController {
    EmployeeService employeeService;

    @PostMapping("/employees")
    public ResponseEntity<CustomResponse<EmployeeModel>> createEmployee(@ModelAttribute EmployeeModel employee){
        CustomResponse<EmployeeModel> response = employeeService.createTeacher(employee);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
