package com.ftbootcamp.eticketgw.client.auth.service;

import com.ftbootcamp.eticketgw.client.auth.AuthClient;
import com.ftbootcamp.eticketgw.client.auth.dto.GenericResponse;
import com.ftbootcamp.eticketgw.client.auth.dto.GenericResponseConstants;
import com.ftbootcamp.eticketgw.client.auth.dto.UserDetailsResponse;
import com.ftbootcamp.eticketgw.exception.AuthClientException;
import com.ftbootcamp.eticketgw.exception.ETicketException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthClientService {

    private final AuthClient authClient;

    public UserDetailsResponse getUserByEmail(String email) {

        try{
            GenericResponse<UserDetailsResponse> response = authClient.getUserByEmail(email);

            if (response.getStatus().equals(GenericResponseConstants.FAILED)) {
                throw new ETicketException("Failed to get user" + response.getMessage());
            }
            return response.getData();

        } catch(FeignException e){
            throw new AuthClientException("Failed to connect to auth service. Please try again later.");
        }
    }
}
