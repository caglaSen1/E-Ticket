package com.ftbootcamp.eticketservice.client.user;

import com.ftbootcamp.eticketservice.client.user.dto.UserDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserClientService {

    private final UserClient userClient;

    public UserDetailsResponse getUserById(Long id) {
        return userClient.getUserById(id).getData();
    }

}
