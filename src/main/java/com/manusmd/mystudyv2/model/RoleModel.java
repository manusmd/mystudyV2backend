package com.manusmd.mystudyv2.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document("roles")
public class RoleModel {
    @Id
    private String id;
    private ERole name;

    public RoleModel(){
    }

    public RoleModel(ERole name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }
}
