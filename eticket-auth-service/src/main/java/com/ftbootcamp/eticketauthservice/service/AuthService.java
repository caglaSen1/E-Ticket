package com.ftbootcamp.eticketauthservice.service;

import com.ftbootcamp.eticketauthservice.client.user.dto.UserDetailsResponse;
import com.ftbootcamp.eticketauthservice.client.user.service.UserClientService;
import com.ftbootcamp.eticketauthservice.dto.request.AdminUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.CompanyUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.IndividualUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.UserLoginRequest;
import com.ftbootcamp.eticketauthservice.dto.response.AdminUserDetailsResponse;
import com.ftbootcamp.eticketauthservice.dto.response.CompanyUserDetailsResponse;
import com.ftbootcamp.eticketauthservice.dto.response.IndividualUserDetailsResponse;
import com.ftbootcamp.eticketauthservice.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserClientService userClientService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminUserDetailsResponse registerAdmin(AdminUserSaveRequest request) {
        return userClientService.createUser(request);
    }

    public CompanyUserDetailsResponse registerCompany(CompanyUserSaveRequest request) {
        return userClientService.createUser(request);
    }

    public IndividualUserDetailsResponse registerIndividual(IndividualUserSaveRequest request) {
        return userClientService.createUser(request);
    }

    public String login(UserLoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword()));

        UserDetailsResponse user = userClientService.getUserByEmail(request.getEmail());

        return jwtUtil.generateToken(user);

    }
}