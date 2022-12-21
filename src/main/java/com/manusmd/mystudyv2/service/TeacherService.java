package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.TeacherModel;
import com.manusmd.mystudyv2.repository.TeacherRepository;
import com.manusmd.mystudyv2.response.CustomResponse;
import com.manusmd.mystudyv2.throwable.ResourceExists;
import com.manusmd.mystudyv2.throwable.ResourceNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherService {
    TeacherRepository teacherRepository;

    public CustomResponse createTeacher(TeacherModel teacher) {
        try {
            TeacherModel.canCreate(teacher, teacherRepository);
            TeacherModel newTeacher = teacherRepository.save(teacher);
            return CustomResponse.CREATED(newTeacher, "Teacher");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceExists e) {
            return CustomResponse.ALREADY_EXISTS(e.getResource(), e.getResourceName(), e.getCheckedProperty());
        }
    }

    public CustomResponse getTeacher(String id) {
        try {
            TeacherModel foundTeacher = TeacherModel.teacherExists(id, teacherRepository);
            return CustomResponse.FOUND(foundTeacher, "Teacher");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getId());
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
            TeacherModel.teacherExists(id, teacherRepository);
            TeacherModel.checkEmailChangeLegit(teacher, teacherRepository);
            teacher.setId(id);
            TeacherModel updatedTeacher = teacherRepository.save(teacher);
            return CustomResponse.OK_PUT(updatedTeacher, "Teacher");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceExists e) {
            return CustomResponse.ALREADY_EXISTS(e.getResource(), e.getResourceName(), e.getCheckedProperty());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getId());
        }
    }

    public CustomResponse toggleStatus(String id) {
        try {
            TeacherModel foundTeacher = TeacherModel.teacherExists(id, teacherRepository);
            foundTeacher.setActive(!foundTeacher.isActive());
            TeacherModel updatedTeacher = teacherRepository.save(foundTeacher);
            return CustomResponse.OK_PUT(updatedTeacher, "Teacher");
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getId());
        }
    }

    public CustomResponse deleteTeacher(String id) {
        try {
            TeacherModel foundTeacher = TeacherModel.teacherExists(id, teacherRepository);
            teacherRepository.deleteById(foundTeacher.getId());
            return CustomResponse.OK_DELETE("Teacher", id);
        } catch (Exception e) {
            return CustomResponse.INTERNAL_SERVER_ERROR(e.getMessage());
        } catch (ResourceNotFound e) {
            return CustomResponse.NOT_FOUND(e.getResource(), e.getId());
        }
    }
}