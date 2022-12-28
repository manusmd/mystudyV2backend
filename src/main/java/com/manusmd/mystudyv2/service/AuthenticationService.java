package com.manusmd.mystudyv2.service;

import com.manusmd.mystudyv2.model.*;
import com.manusmd.mystudyv2.payload.request.LoginRequest;
import com.manusmd.mystudyv2.payload.request.SignupRequest;
import com.manusmd.mystudyv2.payload.response.JwtResponse;
import com.manusmd.mystudyv2.payload.response.MessageResponse;
import com.manusmd.mystudyv2.repository.*;
import com.manusmd.mystudyv2.response.AdminResponse;
import com.manusmd.mystudyv2.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthenticationService {

    AuthenticationManager authenticationManager;
    AuthUserRepository authUserRepository;
    EmployeeRepository employeeRepository;
    TeacherRepository teacherRepository;
    StudentRepository studentRepository;
    RoleRepository roleRepository;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;

    public ResponseEntity<?> signup(SignupRequest signUpRequest) {
        if (authUserRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (authUserRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }



        // Create new user's account
        AuthUserModel user = new AuthUserModel(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        Optional<EmployeeModel> employee = employeeRepository.findByEmail(signUpRequest.getEmail());
        Optional<TeacherModel> teacher = teacherRepository.findByEmail(signUpRequest.getEmail());
        Optional<StudentModel> student = studentRepository.findByEmail(signUpRequest.getEmail());

        try{

        if (employee.isPresent()){
            UserModel.changeUsernameAndSave(signUpRequest, employeeRepository);
            UserModel.checkRoles(employee.get().getRoles());
            Set<RoleModel> roles = UserModel.createRoleModels(employee.get().getRoles(), roleRepository);
            user.setRoles(roles);
        } else if (teacher.isPresent()){
            UserModel.changeUsernameAndSave(signUpRequest, teacherRepository);
            UserModel.checkRoles(teacher.get().getRoles());
            Set<RoleModel> roles = UserModel.createRoleModels(teacher.get().getRoles(), roleRepository);
            user.setRoles(roles);
        } else if (student.isPresent()){
            UserModel.changeUsernameAndSave(signUpRequest, studentRepository);
            UserModel.checkRoles(student.get().getRoles());
            Set<RoleModel> roles = UserModel.createRoleModels(student.get().getRoles(), roleRepository);
            user.setRoles(roles);
        } else {
            Set<RoleModel> roles = UserModel.createRoleModels(signUpRequest.getRoles(), roleRepository);
            user.setRoles(roles);
        }
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
        }


        authUserRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    public ResponseEntity<?> whoami() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        switch (user.getAuthorities().toArray()[0].toString()) {
            case "ROLE_ADMIN" -> {
                AuthUserModel authUserModel = authUserRepository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("Error: User is not found."));
                AdminResponse adminResponse = new AdminResponse();
                adminResponse.setId(authUserModel.getId());
                adminResponse.setUsername(authUserModel.getUsername());
                adminResponse.setEmail(authUserModel.getEmail());
                adminResponse.setRoles(authUserModel.getRoles().stream().map(RoleModel::getName).collect(Collectors.toList()));
                return ResponseEntity.ok(adminResponse);
            }
            case "ROLE_MODERATOR" -> {
                EmployeeModel employee = employeeRepository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("Error: Employee is not found."));
                return ResponseEntity.ok(employee);
            }
            case "ROLE_TEACHER" -> {
                TeacherModel teacher = teacherRepository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("Error: Teacher is not found."));
                return ResponseEntity.ok(teacher);
            }
            case "ROLE_STUDENT" -> {
                StudentModel student = studentRepository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("Error: Student is not found."));
                return ResponseEntity.ok(student);
            }
            default -> {
                return ResponseEntity.ok(new MessageResponse("Failed to get user!"));
            }
        }
    }
}
