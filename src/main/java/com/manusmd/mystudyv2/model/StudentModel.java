package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.StudentRepository;
import com.manusmd.mystudyv2.throwable.ResourceNotFound;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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

    public static void studentsExist(List<String> students, StudentRepository studentRepository) throws ResourceNotFound {
        List<StudentModel> foundStudents = new ArrayList<>();
        for (String student : students) {
            studentRepository.findById(student).ifPresent(foundStudents::add);
        }
        List<String> notFoundStudents = new ArrayList<>();
        for (String student : students) {
            if (foundStudents.stream().noneMatch(foundStudent -> foundStudent.getId().equals(student))) {
                notFoundStudents.add(student);
            }
        }
        if (foundStudents.size() != students.size()) {
            throw new ResourceNotFound("Student", "Student(s) not found: " + notFoundStudents);
        }
    }

}
