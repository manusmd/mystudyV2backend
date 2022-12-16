package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.CustomResponse;
import com.manusmd.mystudyv2.model.EmployeeModel;
import com.manusmd.mystudyv2.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {
    EmployeeRepository employeeRepository;
    public CustomResponse<EmployeeModel> createTeacher(EmployeeModel employee) {
        try {
            Optional<EmployeeModel> foundEmployee = employeeRepository.findByEmail(employee.getEmail());
            if(foundEmployee.isPresent()){
                return new CustomResponse<>(foundEmployee.get(),
                        "There already is an employee with this address: " + employee.getEmail(), HttpStatus.CONFLICT);
            }
            EmployeeModel createdEmployee = employeeRepository.save(employee);
            return new CustomResponse<>(createdEmployee,"Employee with id " + createdEmployee.getId() + " created",
                    HttpStatus.CREATED);
        } catch (Exception e){
            return new CustomResponse<>(null, e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomResponse<EmployeeModel> getEmployee(String id) {
        try {
            Optional<EmployeeModel> foundEmployee = employeeRepository.findById(id);
            if(foundEmployee.isEmpty()){
                return new CustomResponse<>(null, "Employee " + id + " not found", HttpStatus.NOT_FOUND);
            }
            return new CustomResponse<>(foundEmployee.get(), "Employee found", HttpStatus.FOUND);
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
