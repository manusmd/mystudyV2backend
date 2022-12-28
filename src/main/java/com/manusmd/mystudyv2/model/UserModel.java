package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.payload.request.SignupRequest;
import com.manusmd.mystudyv2.repository.EmployeeRepository;
import com.manusmd.mystudyv2.repository.RoleRepository;
import com.manusmd.mystudyv2.repository.StudentRepository;
import com.manusmd.mystudyv2.repository.TeacherRepository;
import com.manusmd.mystudyv2.throwable.ResourceExists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    private Set<String> roles;


    public UserModel(String firstName, String lastName, String email, String street, String house, String city,
                     String postcode, String phone, Set<String> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.street = street;
        this.house = house;
        this.city = city;
        this.postcode = postcode;
        this.phone = phone;
        this.roles = roles;
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

    public static void changeUsernameAndSave(SignupRequest signupRequest, EmployeeRepository repository) {
        Optional<EmployeeModel> foundEmployee = repository.findByEmail(signupRequest.getEmail());
        if (foundEmployee.isEmpty()) throw new RuntimeException("Employee not found");
        foundEmployee.get().setUsername(signupRequest.getUsername());
        repository.save(foundEmployee.get());
    }

    public static void changeUsernameAndSave(SignupRequest signupRequest, TeacherRepository repository) {
        Optional<TeacherModel> foundTeacher = repository.findByEmail(signupRequest.getEmail());
        if (foundTeacher.isEmpty()) throw new RuntimeException("Teacher not found");
        foundTeacher.get().setUsername(signupRequest.getUsername());
        repository.save(foundTeacher.get());
    }

    public static void changeUsernameAndSave(SignupRequest signupRequest, StudentRepository repository) {
        Optional<StudentModel> foundStudent = repository.findByEmail(signupRequest.getEmail());
        if (foundStudent.isEmpty()) throw new RuntimeException("Student not found");
        foundStudent.get().setUsername(signupRequest.getUsername());
        repository.save(foundStudent.get());
    }

    public static Set<RoleModel> createRoleModels(Set<String> strRoles,
                                                  RoleRepository roleRepository) {
        checkRoles(strRoles);
        Set<RoleModel> roles = new HashSet<>();
        if (strRoles.size() == 0) {
            RoleModel userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            try {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "ROLE_ADMIN" -> {
                            RoleModel adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);
                        }
                        case "ROLE_MODERATOR" -> {
                            RoleModel modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(modRole);

                        }
                        case "ROLE_TEACHER" -> {
                            RoleModel teacherRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(teacherRole);

                        }
                        case "ROLE_STUDENT" -> {
                            RoleModel studentRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(studentRole);

                        }
                        default -> throw new RuntimeException("Error: Role is not found.");
                    }
                });
            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return roles;
    }

    public static void checkRoles(Set<String> roles) throws RuntimeException {
        if((roles.contains("ROLE_ADMIN") || roles.contains("ROLE_MODERATOR") || roles.contains("ROLE_TEACHER") ) && roles.size() > 3) {
            throw new RuntimeException("Admin/Employee/Teacher can't have more than 3 roles");
        } else if (roles.contains("ROLE_STUDENT") && roles.size() > 1) {
            throw new RuntimeException("Student can't have more than 1 role");
        }
    }

}
