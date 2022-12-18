package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.StudentModel;
import com.manusmd.mystudyv2.repository.StudentRepository;
import com.manusmd.mystudyv2.response.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {
    StudentRepository studentRepository;
    public CustomResponse<StudentModel> createStudent(StudentModel student) {
        try {
            Optional<StudentModel> foundStudent = studentRepository.findByEmail(student.getEmail());
            if (foundStudent.isPresent()) {
                return new CustomResponse<>(null, "Student already exists", HttpStatus.CONFLICT);
            }
            StudentModel createdStudent = studentRepository.save(student);
            return new CustomResponse<>(createdStudent, "Student " + createdStudent.getId() + " created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new CustomResponse<>(null, "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomResponse<StudentModel> getStudent(String id) {
        try {
             Optional<StudentModel> foundStudent = studentRepository.findById(id);
             if(foundStudent.isEmpty()){
                 return new CustomResponse<>(null,"Student " + id + " not found", HttpStatus.NOT_FOUND);
             }
             return new CustomResponse<>(foundStudent.get(), "Student found", HttpStatus.OK);
        } catch (Exception e){
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomResponse<List<StudentModel>> getAllStudents() {
        try {
            List<StudentModel> foundStudents = studentRepository.findAll();
            return new CustomResponse<>(foundStudents, "Successfully fetched " + foundStudents.size() + " student/s",
                    HttpStatus.OK);
        } catch (Exception e){
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    public CustomResponse<StudentModel> updateStudent(StudentModel student, String id) {
        try {
            Optional<StudentModel> foundStudent = studentRepository.findById(id);
            if(foundStudent.isEmpty()){
                return new CustomResponse<>(null, "No student with id " + id + " found", HttpStatus.NOT_FOUND);
            }
            if(!foundStudent.get().checkEmailChangeLegit(student, studentRepository)){
                return new CustomResponse<>(null, "Email already in use", HttpStatus.CONFLICT);
            }
            student.setId(id);
            StudentModel updatedStudent = studentRepository.save(student);
            return new CustomResponse<>(updatedStudent, "Successfully updated student " + id, HttpStatus.OK);
        } catch (Exception e){
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
