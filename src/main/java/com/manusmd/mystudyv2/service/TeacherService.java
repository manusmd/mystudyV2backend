package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.CustomResponse;
import com.manusmd.mystudyv2.model.TeacherModel;
import com.manusmd.mystudyv2.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TeacherService {
    TeacherRepository teacherRepository;

    public CustomResponse<TeacherModel> createTeacher(TeacherModel teacher) {
        try {
            TeacherModel foundTeacher = teacherRepository.findByEmail(teacher.getEmail());
            if (foundTeacher != null) {
                return new CustomResponse<>(foundTeacher, "Teacher already exists", HttpStatus.CONFLICT);
            }
            TeacherModel newTeacher = teacherRepository.save(teacher);
            return new CustomResponse<>(newTeacher, "Teacher " + newTeacher.getId() + " created successfully",
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new CustomResponse<>(null, "Error creating teacher", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public CustomResponse<TeacherModel> getTeacher(String id) {
        try {
            Optional<TeacherModel> foundTeacher = teacherRepository.findById(id);
            if(foundTeacher.isPresent()){
                return new CustomResponse<>(foundTeacher.get(),"Teacher found", HttpStatus.FOUND);
            } else {
                return new CustomResponse<>(null, "Teacher " + id + " not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new CustomResponse<>(null, "Error while searching for teacher", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
