package com.ftbootcamp.eticketgw.service;

import com.ftbootcamp.eticketgw.client.auth.dto.UserDetailsResponse;
import com.ftbootcamp.eticketgw.client.auth.service.AuthClientService;
import com.ftbootcamp.eticketgw.exception.ETicketException;
import com.ftbootcamp.eticketgw.exception.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final AuthClientService authClientService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetailsResponse userDetailsResponse = authClientService.getUserByEmail(email);

        if (userDetailsResponse == null) {
            throw new ETicketException(ExceptionMessages.USER_NOT_FOUND);
        }
        return User.builder()
                .username(userDetailsResponse.getEmail())
                .password("")
                .build();
    }
}