package com.ftbootcamp.eticketservice.client.user.service;

import com.ftbootcamp.eticketservice.client.user.UserClient;
import com.ftbootcamp.eticketservice.client.user.dto.UserDetailsResponse;
import com.ftbootcamp.eticketservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketservice.dto.response.GenericResponseConstants;
import com.ftbootcamp.eticketservice.exception.ETicketException;
import com.ftbootcamp.eticketservice.exception.UserClientException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserClientService {

    private final UserClient userClient;

    public UserDetailsResponse getUserById(Long id) {

        try{
            GenericResponse<UserDetailsResponse> response = userClient.getUserById(id);

            if (response.getStatus().equals(GenericResponseConstants.FAILED)) {
                throw new ETicketException("Failed to get user" + response.getMessage());
            }
            return response.getData();

        } catch(FeignException e){
            throw new UserClientException("Failed to connect to user service. Please try again later.");
        }

    }

}
