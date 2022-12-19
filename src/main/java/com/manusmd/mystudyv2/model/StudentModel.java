package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.StudentRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@Document("students")
public class StudentModel extends UserModel {
    private Double balance = 0.0;

    public StudentModel(String firstName, String lastName, String email, String street, String house, String city, String postcode, String phone) {
        super(firstName, lastName, email, street, house, city, postcode, phone);
    }

    public StudentModel bookBalanceAndSave(Double value, StudentRepository studentRepository) {
        StudentModel updatedStudent = new StudentModel(this.getFirstName(), this.getLastName(), this.getEmail(),
                this.getStreet(), this.getHouse(), this.getCity(), this.getPostcode(), this.getPhone());
        updatedStudent.setBalance(this.balance + value);
        updatedStudent.setId(this.getId());
        studentRepository.save(updatedStudent);
        return updatedStudent;
    }

}
