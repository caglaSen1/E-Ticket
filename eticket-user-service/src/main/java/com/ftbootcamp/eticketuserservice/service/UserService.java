package com.ftbootcamp.eticketuserservice.service;

import com.ftbootcamp.eticketuserservice.converter.UserConverter;
import com.ftbootcamp.eticketuserservice.dto.response.UserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.UserSummaryResponse;
import com.ftbootcamp.eticketuserservice.repository.UserRepository;
import com.ftbootcamp.eticketuserservice.rules.UserBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserBusinessRules userBusinessRules;

    public List<UserSummaryResponse> getAllUsers() {
        return UserConverter.toUserSummaryResponse(userRepository.findAll());
    }

    public UserDetailsResponse getUserById(Long id) {
        userBusinessRules.checkUserExistById(id);
        return UserConverter.toUserDetailsReaponse(userRepository.findUserWithDetailsById(id).get());
    }

    public UserDetailsResponse getUserByEmail(String email) {
        userBusinessRules.checkUserExistByEmail(email);
        return UserConverter.toUserDetailsReaponse(userRepository.findUserWithDetailsByEmail(email).get());
    }

    public int getHowManyUsers() {
        return (int) userRepository.count();
    }

}
