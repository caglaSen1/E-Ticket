package com.ftbootcamp.eticketauthservice.service;

import com.ftbootcamp.eticketauthservice.client.user.dto.UserDetailsResponse;
import com.ftbootcamp.eticketauthservice.client.user.service.UserClientService;
import com.ftbootcamp.eticketauthservice.exception.ETicketException;
import com.ftbootcamp.eticketauthservice.exception.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserClientService userClientService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetailsResponse userDetailsResponse = userClientService.getUserByEmail(email);

        if (userDetailsResponse == null) {
            throw new ETicketException(ExceptionMessages.USER_NOT_FOUND);
        }
        return User.builder()
                .username(userDetailsResponse.getEmail())
                .password("")
                .build();
    }
}