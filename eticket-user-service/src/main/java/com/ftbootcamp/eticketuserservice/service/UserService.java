package com.ftbootcamp.eticketuserservice.service;

import com.ftbootcamp.eticketuserservice.converter.UserConverter;
import com.ftbootcamp.eticketuserservice.dto.response.UserSummaryPaginatedResponse;
import com.ftbootcamp.eticketuserservice.dto.response.UserDetailsResponse;
import com.ftbootcamp.eticketuserservice.entity.abstracts.User;
import com.ftbootcamp.eticketuserservice.repository.UserRepository;
import com.ftbootcamp.eticketuserservice.rules.UserBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserBusinessRules userBusinessRules;

    public UserSummaryPaginatedResponse getAllUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageRequest);

        return UserConverter.toPaginatedUserSummaryResponse(userPage);
    }

    public UserDetailsResponse getUserById(Long id) {
        userBusinessRules.checkUserExistById(id);
        return UserConverter.toUserDetailsResponse(userRepository.findUserWithDetailsById(id).get());
    }

    public UserDetailsResponse getUserByEmail(String email) {
        userBusinessRules.checkUserExistByEmail(email);
        return UserConverter.toUserDetailsResponse(userRepository.findUserWithDetailsByEmail(email).get());
    }

    public int getHowManyUsers() {
        return (int) userRepository.count();
    }

}
