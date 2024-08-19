package com.ftbootcamp.eticketservice.client.user;

import com.ftbootcamp.eticketservice.client.user.dto.UserDetailsResponse;
import com.ftbootcamp.eticketservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketservice.dto.response.GenericResponseConstants;
import com.ftbootcamp.eticketservice.exception.ETicketException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserClientService {

    private final UserClient userClient;

    public UserDetailsResponse getUserById(Long id) {
        GenericResponse<UserDetailsResponse> response = userClient.getUserById(id);

        if (response.getStatus().equals(GenericResponseConstants.FAILED)) {
            throw new ETicketException(response.getMessage(), "Failed to get user by id");
        }

        return response.getData();
    }

}
