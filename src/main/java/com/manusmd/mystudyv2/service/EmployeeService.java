package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.EmployeeModel;
import com.manusmd.mystudyv2.repository.EmployeeRepository;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.throwable.ResourceExists;
import com.manusmd.mystudyv2.throwable.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    EmployeeRepository employeeRepository;

    public CustomResponse createTeacher(EmployeeModel employee) {
        try {
            EmployeeModel.canCreate(employee, employeeRepository);
            EmployeeModel createdEmployee = employeeRepository.save(employee);
            return CustomResponse.CREATED(createdEmployee, "Employee");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceExists e) {
            return CustomResponse.ALREADY_EXISTS(e.getResource(), e.getResourceName(), e.getCheckedProperty());
        }
    }

    public CustomResponse getEmployee(String id) {
        try {
            EmployeeModel foundEmployee = EmployeeModel.employeeExists(id, employeeRepository);
            return CustomResponse.FOUND(foundEmployee, "Employee");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(),e.getId());
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
            EmployeeModel foundEmployee = EmployeeModel.employeeExists(id, employeeRepository);
            if(!foundEmployee.getEmail().equals(employee.getEmail())){
                EmployeeModel.checkEmailChangeLegit(employee, employeeRepository);
            }
            employee.setId(id);
            EmployeeModel updatedEmployee = employeeRepository.save(employee);
            return CustomResponse.OK_PUT(updatedEmployee, "Employee");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(),e.getId());
        } catch (ResourceExists e) {
            return CustomResponse.ALREADY_EXISTS(e.getResource(),e.getResourceName(),e.getCheckedProperty());
        }
    }

    public CustomResponse toggleStatus(String id) {
        try {
            EmployeeModel foundEmployee = EmployeeModel.employeeExists(id, employeeRepository);
            foundEmployee.setActive(!foundEmployee.isActive());
            EmployeeModel updatedEmployee = employeeRepository.save(foundEmployee);
            return CustomResponse.OK_PUT(updatedEmployee, "Employee");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(),e.getId());
        }
    }

    public CustomResponse deleteEmployee(String id) {
        try {
            EmployeeModel.employeeExists(id, employeeRepository);
            employeeRepository.deleteById(id);
            return CustomResponse.OK_DELETE("Employee", id);
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(),e.getId());
        }
    }
}
