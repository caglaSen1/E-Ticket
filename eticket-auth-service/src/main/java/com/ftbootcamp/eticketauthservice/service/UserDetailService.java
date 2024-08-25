package com.ftbootcamp.eticketauthservice.service;

import com.ftbootcamp.eticketauthservice.converter.UserConverter;
import com.ftbootcamp.eticketauthservice.entity.abstracts.User;
import com.ftbootcamp.eticketauthservice.exception.ETicketException;
import com.ftbootcamp.eticketauthservice.exception.ExceptionMessages;
import com.ftbootcamp.eticketauthservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ETicketException(ExceptionMessages.USER_NOT_FOUND));

        return UserConverter.toCustomUser(user);
    }
}
