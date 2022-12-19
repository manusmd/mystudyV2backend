package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.EmployeeModel;
import com.manusmd.mystudyv2.repository.EmployeeRepository;
import com.manusmd.mystudyv2.response.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {
    EmployeeRepository employeeRepository;

    public CustomResponse createTeacher(EmployeeModel employee) {
        try {
            Optional<EmployeeModel> foundEmployee = employeeRepository.findByEmail(employee.getEmail());
            if (foundEmployee.isPresent()) {
                return CustomResponse.ALREADY_EXISTS(foundEmployee.get(), "Employee", "mail");
            }
            EmployeeModel createdEmployee = employeeRepository.save(employee);
            return CustomResponse.CREATED(createdEmployee, "Employee");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse getEmployee(String id) {
        try {
            Optional<EmployeeModel> foundEmployee = employeeRepository.findById(id);
            if (foundEmployee.isEmpty()) {
                return CustomResponse.NOT_FOUND("Employee", id);
            }
            return CustomResponse.FOUND(foundEmployee.get(), "Employee");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse getAllEmployees() {
        try {
            List<EmployeeModel> foundEmployees = employeeRepository.findAll();
            return CustomResponse.FOUND_FETCHED_LIST(foundEmployees, "Employee");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse updateEmployee(EmployeeModel employee, String id) {
        try {
            Optional<EmployeeModel> foundEmployee = employeeRepository.findById(id);
            if (foundEmployee.isEmpty()) {
                return CustomResponse.NOT_FOUND("Employee", id);
            }
            if (!foundEmployee.get().checkEmailChangeLegit(employee, employeeRepository)) {
                return CustomResponse.ALREADY_EXISTS(employee, "Employee", "mail");
            }
            employee.setId(id);
            EmployeeModel updatedEmployee = employeeRepository.save(employee);
            return CustomResponse.OK_PUT(updatedEmployee, "Employee");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse toggleStatus(String id) {
        try {
            Optional<EmployeeModel> foundEmployee = employeeRepository.findById(id);
            if (foundEmployee.isEmpty()) {
                return CustomResponse.NOT_FOUND("Employee", id);
            }
            EmployeeModel employee = foundEmployee.get();
            employee.setActive(!employee.isActive());
            EmployeeModel updatedEmployee = employeeRepository.save(employee);
            return CustomResponse.OK_PUT(updatedEmployee, "Employee");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse deleteEmployee(String id) {
        try {
            Optional<EmployeeModel> foundEmployee = employeeRepository.findById(id);
            if (foundEmployee.isEmpty()) {
                return CustomResponse.NOT_FOUND("Employee", id);
            }
            employeeRepository.deleteById(id);
            return CustomResponse.OK_DELETE("Employee", id);
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }
}
