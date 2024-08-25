package com.ftbootcamp.eticketauthservice.controller;

import com.ftbootcamp.eticketauthservice.client.user.service.UserClientService;
import com.ftbootcamp.eticketauthservice.dto.request.AdminUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.CompanyUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.IndividualUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.UserLoginRequest;
import com.ftbootcamp.eticketauthservice.dto.response.AdminUserDetailsResponse;
import com.ftbootcamp.eticketauthservice.dto.response.CompanyUserDetailsResponse;
import com.ftbootcamp.eticketauthservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketauthservice.dto.response.IndividualUserDetailsResponse;
import com.ftbootcamp.eticketauthservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final UserClientService userClientService;

    @PostMapping(path = "/register-admin")
    public GenericResponse<AdminUserDetailsResponse> registerAdmin(@RequestBody AdminUserSaveRequest request){
        return GenericResponse.success(authService.registerAdmin(request), HttpStatus.CREATED);
    }

    @PostMapping(path = "/register-company")
    public GenericResponse<CompanyUserDetailsResponse> registerCompany(@RequestBody CompanyUserSaveRequest request){
        return GenericResponse.success(authService.registerCompany(request), HttpStatus.CREATED);
    }

    @PostMapping(path = "/register-individual")
    public GenericResponse<IndividualUserDetailsResponse> registerIndividual(@RequestBody IndividualUserSaveRequest request){
        return GenericResponse.success(authService.registerIndividual(request), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public GenericResponse<String> login(@RequestBody UserLoginRequest request) {
        return GenericResponse.success(authService.login(request), HttpStatus.OK);
    }
}