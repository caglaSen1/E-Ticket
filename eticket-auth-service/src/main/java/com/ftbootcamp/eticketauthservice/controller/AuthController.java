package com.ftbootcamp.eticketauthservice.controller;

import com.ftbootcamp.eticketauthservice.dto.request.AdminUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.CompanyUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.IndividualUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.UserLoginRequest;
import com.ftbootcamp.eticketauthservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketauthservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth API V1", description = "Auth API for users login and register operations")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/login")
    @Operation(summary = "Login", description = "Login with all user types")
    public GenericResponse<String> login(@Valid @RequestBody UserLoginRequest request) {
        return GenericResponse.success(authService.login(request), HttpStatus.OK);
    }

    @PostMapping(path = "/register-admin")
    @Operation(summary = "Register Admin", description = "Register Admin with given credentials")
    public GenericResponse<Void> registerAdmin(@Valid @RequestBody AdminUserSaveRequest request){
        authService.registerAdmin(request);
        return GenericResponse.success(null, HttpStatus.CREATED);
    }

    @PostMapping(path = "/register-company")
    @Operation(summary = "Register Company", description = "Register Company with given credentials")
    public GenericResponse<Void> registerCompany(@Valid @RequestBody CompanyUserSaveRequest request){
        authService.registerCompany(request);
        return GenericResponse.success(null, HttpStatus.CREATED);
    }

    @PostMapping(path = "/register-individual")
    @Operation(summary = "Register Individual", description = "Register Individual with given credentials")
    public GenericResponse<Void> registerIndividual(@Valid @RequestBody IndividualUserSaveRequest request){
        authService.registerIndividual(request);
        return GenericResponse.success(null, HttpStatus.CREATED);
    }
}
