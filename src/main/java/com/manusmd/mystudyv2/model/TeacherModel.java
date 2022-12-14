package com.manusmd.mystudyv2.model;

import com.manusmd.mystudyv2.repository.TeacherRepository;
import com.manusmd.mystudyv2.throwable.ResourceExists;
import com.manusmd.mystudyv2.throwable.ResourceNotFound;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)

@Document(collection = "teachers")
public class TeacherModel extends UserModel {
    private List<String> subjects;
    private double hourlyRate;

    public TeacherModel(String firstName, String lastName, String email, String street, String house, String city,
                        String postcode, String phone, List<String> subjects, double hourlyRate, Set<String> roles) {
        super(firstName, lastName, email, street, house, city, postcode, phone, roles);
        this.subjects = subjects;
        this.hourlyRate = hourlyRate;
    }

    public static void canCreate(TeacherModel teacher, TeacherRepository teacherRepository) throws ResourceExists {
        Optional<TeacherModel> foundTeacher = teacherRepository.findByEmail(teacher.getEmail());
        if (foundTeacher.isPresent()) {
            throw new ResourceExists(teacher, "Teacher", "mail");
        }
    }

    public static TeacherModel teacherExists(String id, TeacherRepository teacherRepository) throws ResourceNotFound {
        Optional<TeacherModel> teacher = teacherRepository.findById(id);
        if (teacher.isEmpty()) {
            throw new ResourceNotFound("Teacher", id);
        }
        return teacher.get();
    }

}
