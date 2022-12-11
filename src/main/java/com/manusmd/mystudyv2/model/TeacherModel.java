package com.manusmd.mystudyv2.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "teachers")
public class TeacherModel {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String street;
    private String house;
    private String city;
    private String postcode;
    private String phone;
    private List<String> subjects;
    private double hourlyRate;

    public TeacherModel(String firstName, String lastName, String email, String street, String house, String city, String postcode, String phone, List<String> subjects, double hourlyRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.street = street;
        this.house = house;
        this.city = city;
        this.postcode = postcode;
        this.phone = phone;
        this.subjects = subjects;
        this.hourlyRate = hourlyRate;
    }
}
