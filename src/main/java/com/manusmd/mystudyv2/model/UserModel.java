package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.payload.request.SignupRequest;
import com.manusmd.mystudyv2.repository.EmployeeRepository;
import com.manusmd.mystudyv2.repository.StudentRepository;
import com.manusmd.mystudyv2.repository.TeacherRepository;
import com.manusmd.mystudyv2.throwable.ResourceExists;
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
    private String username;
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

    public static void checkEmailChangeLegit(TeacherModel teacher, TeacherRepository repository) throws ResourceExists {
        Optional<TeacherModel> foundTeacherByEmail = repository.findByEmail(teacher.getEmail());
        if (foundTeacherByEmail.isPresent()) {
            throw new ResourceExists(teacher, "Teacher", "mail");
        }
    }

    public static void checkEmailChangeLegit(EmployeeModel employee, EmployeeRepository repository) throws ResourceExists {
        Optional<EmployeeModel> foundEmployeeByEmail = repository.findByEmail(employee.getEmail());
        if (foundEmployeeByEmail.isPresent()) {
            throw new ResourceExists(employee, "Employee", "mail");
        }
    }

    public static void checkEmailChangeLegit(StudentModel student, StudentRepository repository) throws ResourceExists {
        Optional<StudentModel> foundStudentByEmail = repository.findByEmail(student.getEmail());
        if (foundStudentByEmail.isPresent()) {
            throw new ResourceExists(student, "Student", "mail");
        }
    }

    public static boolean changeUsernameAndSave(SignupRequest signupRequest, EmployeeRepository repository) {
        Optional<EmployeeModel> foundEmployee = repository.findByEmail(signupRequest.getEmail());
        if (foundEmployee.isEmpty()) return false;
        foundEmployee.get().setUsername(signupRequest.getUsername());
        repository.save(foundEmployee.get());
        return true;
    }

    public static boolean changeUsernameAndSave(SignupRequest signupRequest, TeacherRepository repository) {
        Optional<TeacherModel> foundTeacher = repository.findByEmail(signupRequest.getEmail());
        if (foundTeacher.isEmpty()) return false;
        foundTeacher.get().setUsername(signupRequest.getUsername());
        repository.save(foundTeacher.get());
        return true;
    }

    public static boolean changeUsernameAndSave(SignupRequest signupRequest, StudentRepository repository) {
        Optional<StudentModel> foundStudent = repository.findByEmail(signupRequest.getEmail());
        if (foundStudent.isEmpty()) return false;
        foundStudent.get().setUsername(signupRequest.getUsername());
        repository.save(foundStudent.get());
        return true;
    }


}
