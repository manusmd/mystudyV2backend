package com.manusmd.mystudyv2.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@Document("employees")
public class EmployeeModel extends UserModel {

    private Double hourlyRate;


    public EmployeeModel(String firstName, String lastName, String email, String street, String house, String city,
                         String postcode, String phone, Double hourlyRate) {
        super(firstName, lastName, email, street, house, city, postcode, phone);
        this.hourlyRate = hourlyRate;
    }


}
