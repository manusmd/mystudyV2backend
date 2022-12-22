package com.manusmd.mystudyv2.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
@Data
@Document("auth_users")
public class AuthUserModel {
    @Id
    private String id;

    @NotBlank
    @Size(max=20)
    private String username;

    @NotBlank
    @Size(max=50)
    @Email
    private String email;

    @NotBlank
    @Size(max=120)
    private String password;

    @DBRef
    private Set<RoleModel> roles = new HashSet<>();

}
