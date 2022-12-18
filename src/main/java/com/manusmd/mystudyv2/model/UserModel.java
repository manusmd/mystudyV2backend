package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.EmployeeRepository;
import com.manusmd.mystudyv2.repository.StudentRepository;
import com.manusmd.mystudyv2.repository.TeacherRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Optional;

@Data
@NoArgsConstructor
public class UserModel {
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
    private boolean active = true;


    public UserModel(String firstName, String lastName, String email, String street, String house, String city, String postcode, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.street = street;
        this.house = house;
        this.city = city;
        this.postcode = postcode;
        this.phone = phone;
    }

    public boolean checkEmailChangeLegit (UserModel employee, TeacherRepository repository) {
        Optional<TeacherModel> foundEmployeeByEmail = repository.findByEmail(employee.getEmail());
        return foundEmployeeByEmail.isEmpty();
    }
    public boolean checkEmailChangeLegit (UserModel employee, EmployeeRepository repository) {
        Optional<EmployeeModel> foundEmployeeByEmail = repository.findByEmail(employee.getEmail());
        return foundEmployeeByEmail.isEmpty();
    }
    public boolean checkEmailChangeLegit (UserModel employee, StudentRepository repository) {
        Optional<StudentModel> foundStudentByEmail = repository.findByEmail(employee.getEmail());
        return foundStudentByEmail.isEmpty();
    }


}
