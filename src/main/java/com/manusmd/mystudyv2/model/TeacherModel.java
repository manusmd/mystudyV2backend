package com.manusmd.mystudyv2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
}
