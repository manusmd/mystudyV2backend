package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.TeacherModel;
import com.manusmd.mystudyv2.repository.TeacherRepository;
import com.manusmd.mystudyv2.response.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeacherService {
    TeacherRepository teacherRepository;

    public CustomResponse createTeacher(TeacherModel teacher) {
        try {
            Optional<TeacherModel> foundTeacher = teacherRepository.findByEmail(teacher.getEmail());
            if (foundTeacher.isPresent()) {
                return CustomResponse.ALREADY_EXISTS(teacher, "Teacher", "mail");
            }
            TeacherModel newTeacher = teacherRepository.save(teacher);
            return CustomResponse.CREATED(newTeacher, "Teacher");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse getTeacher(String id) {
        try {
            Optional<TeacherModel> foundTeacher = teacherRepository.findById(id);
            if (foundTeacher.isEmpty()) {
                return CustomResponse.NOT_FOUND("Teacher", id);
            }
            return CustomResponse.FOUND(foundTeacher.get(), "Teacher");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse getAllTeachers() {
        try {
            List<TeacherModel> foundTeachers = teacherRepository.findAll();
            return CustomResponse.FOUND_FETCHED_LIST(foundTeachers, "Teacher");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse updateTeacher(String id, TeacherModel teacher) {
        try {
            Optional<TeacherModel> foundTeacher = teacherRepository.findById(id);
            if (foundTeacher.isEmpty()) {
                return CustomResponse.NOT_FOUND("Teacher", id);
            }
            if (!foundTeacher.get().checkEmailChangeLegit(teacher, teacherRepository)) {
                return CustomResponse.ALREADY_EXISTS(teacher, "Teacher", "mail");
            }
            teacher.setId(id);
            TeacherModel updatedTeacher = teacherRepository.save(teacher);
            return CustomResponse.OK_PUT(updatedTeacher, "Teacher");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse toggleStatus(String id) {
        try {
            Optional<TeacherModel> foundTeacher = teacherRepository.findById(id);
            if (foundTeacher.isEmpty()) {
                return CustomResponse.NOT_FOUND("Teacher", id);
            }
            TeacherModel teacher = foundTeacher.get();
            teacher.setActive(!teacher.isActive());
            TeacherModel updatedTeacher = teacherRepository.save(teacher);
            return CustomResponse.OK_PUT(updatedTeacher, "Teacher");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }

    public CustomResponse deleteTeacher(String id) {
        try {
            Optional<TeacherModel> foundTeacher = teacherRepository.findById(id);
            if (foundTeacher.isEmpty()) {
                return CustomResponse.NOT_FOUND("Teacher", id);
            }
            teacherRepository.deleteById(id);
            return CustomResponse.OK_DELETE("Teacher", id);
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        }
    }
}