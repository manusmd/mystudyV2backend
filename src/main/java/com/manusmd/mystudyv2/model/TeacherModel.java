package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.TeacherRepository;
import com.manusmd.mystudyv2.throwable.ResourceNotFound;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)

@Document(collection = "teachers")
public class TeacherModel extends UserModel {
    private List<String> subjects;
    private double hourlyRate;

    public TeacherModel(String firstName, String lastName, String email, String street, String house, String city, String postcode, String phone, List<String> subjects, double hourlyRate) {
        super(firstName, lastName, email, street, house, city, postcode, phone);
        this.subjects = subjects;
        this.hourlyRate = hourlyRate;
    }

    public static void teacherExists(String id, TeacherRepository teacherRepository) throws ResourceNotFound {
        Optional<TeacherModel> teacher = teacherRepository.findById(id);
        if (teacher.isEmpty()) {
            throw new ResourceNotFound("Teacher", "Teacher not found: " + id);
        }
    }
}
