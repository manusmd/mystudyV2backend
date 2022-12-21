package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.EmployeeRepository;
import com.manusmd.mystudyv2.throwable.ResourceExists;
import com.manusmd.mystudyv2.throwable.ResourceNotFound;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)
@Document("employees")
public class EmployeeModel extends UserModel {

    private Double hourlyRate;


    public EmployeeModel(String firstName, String lastName, String email, String street, String house, String city, String postcode, String phone, Double hourlyRate) {
        super(firstName, lastName, email, street, house, city, postcode, phone);
        this.hourlyRate = hourlyRate;
    }

    public static void checkCreationWithThrowable(EmployeeModel employee, EmployeeRepository employeeRepository) throws ResourceExists {
        Optional<EmployeeModel> foundEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (foundEmployee.isPresent()) {
            throw new ResourceExists(employee, "Employee", "mail");
        }
    }

    public static void employeeExists(String id, EmployeeRepository employeeRepository) throws ResourceNotFound {
        Optional<EmployeeModel> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new ResourceNotFound("Employee", id);
        }
    }
}
