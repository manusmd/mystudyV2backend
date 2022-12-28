package com.manusmd.mystudyv2.response;

import com.manusmd.mystudyv2.model.ERole;
import lombok.Data;

import java.util.List;

@Data
public class AdminResponse {
    private String id;
    private String username;
    private String email;
    private List<ERole> roles;
}
