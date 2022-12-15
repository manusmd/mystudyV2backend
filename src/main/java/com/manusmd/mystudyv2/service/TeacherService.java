package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.CustomResponse;
import com.manusmd.mystudyv2.model.TeacherModel;
import com.manusmd.mystudyv2.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeacherService {
    TeacherRepository teacherRepository;

    public CustomResponse<TeacherModel> createTeacher(TeacherModel teacher) {
        try {
            Optional<TeacherModel> foundTeacher = teacherRepository.findByEmail(teacher.getEmail());
            if (foundTeacher.isPresent()) {
                return new CustomResponse<>(foundTeacher.get(), "Teacher already exists", HttpStatus.CONFLICT);
            }
            TeacherModel newTeacher = teacherRepository.save(teacher);
            return new CustomResponse<>(newTeacher, "Teacher " + newTeacher.getId() + " created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public CustomResponse<TeacherModel> getTeacher(String id) {
        try {
            Optional<TeacherModel> foundTeacher = teacherRepository.findById(id);
            if (foundTeacher.isPresent()) {
                return new CustomResponse<>(foundTeacher.get(), "Teacher found", HttpStatus.FOUND);
            } else {
                return new CustomResponse<>(null, "Teacher " + id + " not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomResponse<List<TeacherModel>> getAllTeachers() {
        try {
            List<TeacherModel> allTeachers = teacherRepository.findAll();
            return new CustomResponse<>(allTeachers, "All teachers successfully fetched", HttpStatus.OK);
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomResponse<TeacherModel> updateTeacher(String id, TeacherModel teacher) {
        try {
            Optional<TeacherModel> foundTeacher = teacherRepository.findById(id);
            Optional<TeacherModel> foundTeacherByEmail = teacherRepository.findByEmail(teacher.getEmail());
            if (foundTeacher.isPresent()) {
                if (!teacher.getEmail()
                        .equals(foundTeacher.get()
                                .getEmail()) && foundTeacherByEmail.isPresent() && !Objects.equals(foundTeacherByEmail.get()
                        .getId(), id)) {
                    return new CustomResponse<>(teacher, "There is already a user with the mail address " + teacher.getEmail(), HttpStatus.CONFLICT);

                }
                TeacherModel updatedTeacher = foundTeacher.get();
                updatedTeacher.setFirstName(teacher.getFirstName());
                updatedTeacher.setLastName(teacher.getLastName());
                updatedTeacher.setEmail(teacher.getEmail());
                updatedTeacher.setStreet(teacher.getStreet());
                updatedTeacher.setHouse(teacher.getHouse());
                updatedTeacher.setCity(teacher.getCity());
                updatedTeacher.setPostcode(teacher.getPostcode());
                updatedTeacher.setPhone(teacher.getPhone());
                updatedTeacher.setSubjects(teacher.getSubjects());
                updatedTeacher.setHourlyRate(teacher.getHourlyRate());
                teacherRepository.save(updatedTeacher);
                return new CustomResponse<>(updatedTeacher, "Teacher " + id + " successfully updated", HttpStatus.OK);
            } else {
                return new CustomResponse<>(null, "Teacher " + id + " not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
