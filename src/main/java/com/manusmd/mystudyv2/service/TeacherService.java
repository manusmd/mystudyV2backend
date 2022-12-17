package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.CustomResponse;
import com.manusmd.mystudyv2.model.TeacherModel;
import com.manusmd.mystudyv2.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeacherService {
    TeacherRepository teacherRepository;

    public CustomResponse<TeacherModel> createTeacher(TeacherModel teacher) {
        try {
            Optional<TeacherModel> foundTeacher = teacherRepository.findByEmail(teacher.getEmail());
            if (foundTeacher.isPresent()) {
                return new CustomResponse<>(foundTeacher.get(), "A teacher with this mail address already exists",
                        HttpStatus.CONFLICT);
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
            if (foundTeacher.isEmpty()) {
                return new CustomResponse<>(null, "Teacher " + id + " not found", HttpStatus.NOT_FOUND);
            }
            if (!foundTeacher.get().checkEmailChangeLegit(teacher, teacherRepository)) {
                return new CustomResponse<>(null, "Email already in use", HttpStatus.CONFLICT);
            }

            teacher.setId(id);
            TeacherModel updatedTeacher = teacherRepository.save(teacher);
            return new CustomResponse<>(updatedTeacher, "Teacher " + id + " successfully updated", HttpStatus.OK);

        } catch (
                Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public CustomResponse<TeacherModel> toggleStatus(String id) {
        try {
            Optional<TeacherModel> foundTeacher = teacherRepository.findById(id);
            if (foundTeacher.isPresent()) {
                TeacherModel updatedTeacher = foundTeacher.get();
                updatedTeacher.setActive(!updatedTeacher.isActive());
                teacherRepository.save(updatedTeacher);
                return new CustomResponse<>(updatedTeacher,
                        "Teacher " + id + "active state is set to: " + updatedTeacher.isActive(), HttpStatus.OK);
            } else {
                return new CustomResponse<>(null, "Teacher " + id + " not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CustomResponse<TeacherModel> deleteTeacher(String id) {
        try {
            Optional<TeacherModel> foundTeacher = teacherRepository.findById(id);
            if (foundTeacher.isPresent()) {
                teacherRepository.deleteById(id);
                return new CustomResponse<>(foundTeacher.get(), "Teacher " + id + " successfully deleted",
                        HttpStatus.OK);
            } else {
                return new CustomResponse<>(null, "Teacher " + id + " not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new CustomResponse<>(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
