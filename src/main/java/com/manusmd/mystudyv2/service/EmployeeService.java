package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.model.EmployeeModel;
import com.manusmd.mystudyv2.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {
    EmployeeRepository employeeRepository;

    public CustomResponse<EmployeeModel> createTeacher(EmployeeModel employee) {
        try {
            Optional<EmployeeModel> foundEmployee = employeeRepository.findByEmail(employee.getEmail());
            if (foundEmployee.isPresent()) {
                return new CustomResponse<>(foundEmployee.get(),
                        "An employee with this mail address already exists", HttpStatus.CONFLICT);
            }
            EmployeeModel createdEmployee = employeeRepository.save(employee);
            return new CustomResponse<>(createdEmployee, "Employee " + createdEmployee.getId() + " created " +
                    "successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomResponse<EmployeeModel> getEmployee(String id) {
        try {
            Optional<EmployeeModel> foundEmployee = employeeRepository.findById(id);
            if (foundEmployee.isEmpty()) {
                return new CustomResponse<>(null, "Employee " + id + " not found", HttpStatus.NOT_FOUND);
            }
            return new CustomResponse<>(foundEmployee.get(), "Employee found", HttpStatus.FOUND);
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomResponse<List<EmployeeModel>> getAllEmployees() {
        try {
            List<EmployeeModel> foundEmployees = employeeRepository.findAll();
            return new CustomResponse<>(foundEmployees, "Successfully fetched " + foundEmployees.size() + " employee" +
                    "/s", HttpStatus.FOUND);
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomResponse<EmployeeModel> updateEmployee(EmployeeModel employee, String id) {
        try {
            Optional<EmployeeModel> foundEmployee = employeeRepository.findById(id);

            if (foundEmployee.isEmpty()) {
                return new CustomResponse<>(null, "No employee with id " + id + " found", HttpStatus.NOT_FOUND);
            }
            if (!foundEmployee.get().checkEmailChangeLegit(employee, employeeRepository)) {
                return new CustomResponse<>(null, "Email already in use", HttpStatus.CONFLICT);

            }
            employee.setId(id);
            EmployeeModel updatedEmployee = employeeRepository.save(employee);
            return new CustomResponse<>(updatedEmployee, "Successfully updated employee " + id, HttpStatus.OK);

        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomResponse<EmployeeModel> toggleStatus(String id) {
        try {
            Optional<EmployeeModel> foundEmployee = employeeRepository.findById(id);
            if (foundEmployee.isEmpty()) {
                return new CustomResponse<>(null, "No employee with id " + id + " found", HttpStatus.NOT_FOUND);
            }
            EmployeeModel employee = foundEmployee.get();
            employee.setActive(!employee.isActive());
            EmployeeModel updatedEmployee = employeeRepository.save(employee);
            return new CustomResponse<>(updatedEmployee,
                    "Employee " + id + "active state is set to: " + updatedEmployee.isActive(),
                    HttpStatus.OK);

        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomResponse<EmployeeModel> deleteEmployee(String id) {
        try {
            Optional<EmployeeModel> foundEmployee = employeeRepository.findById(id);
            if (foundEmployee.isEmpty()) {
                return new CustomResponse<>(null, "No employee with id " + id + " found", HttpStatus.NOT_FOUND);
            }
            employeeRepository.deleteById(id);
            return new CustomResponse<>(null, "Employee " + id + " deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
