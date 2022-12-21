package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.StudentModel;
import com.manusmd.mystudyv2.repository.StudentRepository;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.throwable.ResourceExists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {
    StudentRepository studentRepository;
    public CustomResponse createStudent(StudentModel student) {
        try {
            Optional<StudentModel> foundStudent = studentRepository.findByEmail(student.getEmail());
            if (foundStudent.isPresent()) {
                return CustomResponse.ALREADY_EXISTS(student,"Student", "mail");
            }
            StudentModel createdStudent = studentRepository.save(student);
            return CustomResponse.CREATED(createdStudent, "Student");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }
    public CustomResponse getStudent(String id) {
        try {
            Optional<StudentModel> foundStudent = studentRepository.findById(id);
            if (foundStudent.isEmpty()) {
                return CustomResponse.NOT_FOUND("Student", id);
            }
            return CustomResponse.FOUND(foundStudent.get(), "Student");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }
    public CustomResponse getAllStudents() {
        try {
            List<StudentModel> foundStudents = studentRepository.findAll();
            return CustomResponse.FOUND_FETCHED_LIST(foundStudents,"Student");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }
    public CustomResponse updateStudent(StudentModel student, String id) {
        try {
            Optional<StudentModel> foundStudent = studentRepository.findById(id);
            if (foundStudent.isEmpty()) {
                return CustomResponse.NOT_FOUND("Student", id);
            }
            StudentModel.checkEmailChangeLegit(student, studentRepository);
            student.setId(id);
            StudentModel updatedStudent = studentRepository.save(student);
            return CustomResponse.OK_PUT(updatedStudent, "Student");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceExists e) {
            return CustomResponse.ALREADY_EXISTS(e.getResource(), e.getResourceName(),e.getCheckedProperty());

        }
    }
    public CustomResponse toggleStatus(String id) {
        try {
            Optional<StudentModel> foundStudent = studentRepository.findById(id);
            if (foundStudent.isEmpty()) {
                return CustomResponse.NOT_FOUND("Student", id);
            }
            StudentModel student = foundStudent.get();
            student.setActive(!student.isActive());
            StudentModel updatedStudent = studentRepository.save(student);
            return CustomResponse.OK_PUT(updatedStudent, "Student");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }
    public CustomResponse deleteStudent(String id) {
        try {
            Optional<StudentModel> foundStudent = studentRepository.findById(id);
            if (foundStudent.isEmpty()) {
                return CustomResponse.NOT_FOUND("Student", id);
            }
            studentRepository.deleteById(id);
            return CustomResponse.OK_DELETE("Student", id);
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }
}