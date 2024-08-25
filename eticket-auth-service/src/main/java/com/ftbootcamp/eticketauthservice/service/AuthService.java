package com.ftbootcamp.eticketauthservice.service;

import com.ftbootcamp.eticketauthservice.client.user.service.UserClientService;
import com.ftbootcamp.eticketauthservice.constants.PasswordConstants;
import com.ftbootcamp.eticketauthservice.dto.request.AdminUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.CompanyUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.IndividualUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.UserLoginRequest;
import com.ftbootcamp.eticketauthservice.dto.response.AdminUserDetailsResponse;
import com.ftbootcamp.eticketauthservice.dto.response.CompanyUserDetailsResponse;
import com.ftbootcamp.eticketauthservice.dto.response.IndividualUserDetailsResponse;
import com.ftbootcamp.eticketauthservice.exception.ETicketException;
import com.ftbootcamp.eticketauthservice.exception.ExceptionMessages;
import com.ftbootcamp.eticketauthservice.model.User;
import com.ftbootcamp.eticketauthservice.repository.UserRepository;
import com.ftbootcamp.eticketauthservice.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserClientService userClientService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminUserDetailsResponse registerAdmin(AdminUserSaveRequest request) {
        checkPasswordValid(request.getPassword());
        request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        return userClientService.createUser(request);
    }

    public CompanyUserDetailsResponse registerCompany(CompanyUserSaveRequest request) {
        checkPasswordValid(request.getPassword());
        request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        return userClientService.createUser(request);
    }

    public IndividualUserDetailsResponse registerIndividual(IndividualUserSaveRequest request) {
        checkPasswordValid(request.getPassword());
        request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        return userClientService.createUser(request);
    }

    public String login(UserLoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        return jwtUtil.generateToken(user);
    }

    private void checkPasswordValid(String password) {
        int minPasswordLength = PasswordConstants.PASSWORD_MIN_LENGTH;
        int maxPasswordLength = PasswordConstants.PASSWORD_MAX_LENGTH;

        if (!(minPasswordLength <= password.length() && maxPasswordLength >= password.length())) {
            throw new ETicketException(ExceptionMessages.USER_PASSWORD_NOT_VALID + " Password: " + password);
        }
    }
}