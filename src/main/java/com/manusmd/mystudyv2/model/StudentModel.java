package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.StudentRepository;
import com.manusmd.mystudyv2.throwable.ResourceExists;
import com.manusmd.mystudyv2.throwable.ResourceNotFound;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Document("students")
public class StudentModel extends UserModel {
    private Double balance = 0.0;

    public StudentModel(String firstName, String lastName, String email, String street, String house, String city,
                        String postcode, String phone, Set<String> roles) {
        super(firstName, lastName, email, street, house, city, postcode, phone, roles);
    }

    public StudentModel bookBalanceAndSave(Double value, StudentRepository studentRepository) {
        StudentModel updatedStudent = new StudentModel(this.getFirstName(), this.getLastName(), this.getEmail(),
                this.getStreet(), this.getHouse(), this.getCity(), this.getPostcode(), this.getPhone(), this.getRoles());
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

    public static void canCreate(StudentModel student, StudentRepository studentRepository) throws ResourceExists {
        Optional<StudentModel> foundStudent = studentRepository.findByEmail(student.getEmail());
        if (foundStudent.isPresent()) {
            throw new ResourceExists(student, "Student", "mail");
        }
    }

    public static StudentModel studentExists(String id, StudentRepository studentRepository) throws ResourceNotFound {
        Optional<StudentModel> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new ResourceNotFound("Student", id);
        }
        return student.get();
    }
}
