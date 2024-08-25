package com.ftbootcamp.eticketauthservice.controller;

import com.ftbootcamp.eticketauthservice.dto.request.AdminUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.CompanyUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.IndividualUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.UserLoginRequest;
import com.ftbootcamp.eticketauthservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketauthservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/login")
    public GenericResponse<String> login(@RequestBody UserLoginRequest request) {
        return GenericResponse.success(authService.login(request), HttpStatus.OK);
    }

    @PostMapping(path = "/register-admin")
    public GenericResponse<Void> registerAdmin(@RequestBody AdminUserSaveRequest request){
        authService.registerAdmin(request);
        return GenericResponse.success(null, HttpStatus.CREATED);
    }

    @PostMapping(path = "/register-company")
    public GenericResponse<Void> registerCompany(@RequestBody CompanyUserSaveRequest request){
        authService.registerCompany(request);
        return GenericResponse.success(null, HttpStatus.CREATED);
    }

    @PostMapping(path = "/register-individual")
    public GenericResponse<Void> registerIndividual(@RequestBody IndividualUserSaveRequest request){
        authService.registerIndividual(request);
        return GenericResponse.success(null, HttpStatus.CREATED);
    }
}
