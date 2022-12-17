package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.EmployeeRepository;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Optional;

@Data
@Document("employees")
public class EmployeeModel {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String street;
    private String house;
    private String city;
    private String postcode;
    private String phone;
    private Double hourlyRate;
    private boolean active = true;

    public EmployeeModel(String firstName, String lastName, String email, String street, String house, String city,
                         String postcode, String phone, Double hourlyRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.street = street;
        this.house = house;
        this.city = city;
        this.postcode = postcode;
        this.phone = phone;
        this.hourlyRate = hourlyRate;
    }

    public boolean checkEmailChangeLegit(EmployeeModel employee, EmployeeRepository employeeRepository){
        Optional<EmployeeModel> foundEmployeeByEmail = employeeRepository.findByEmail(employee.getEmail());
        return foundEmployeeByEmail.isEmpty();

    }
}
