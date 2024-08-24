package com.ftbootcamp.eticketauthservice.client.user.service;

import com.ftbootcamp.eticketauthservice.client.user.UserClient;
import com.ftbootcamp.eticketauthservice.client.user.dto.UserDetailsResponse;
import com.ftbootcamp.eticketauthservice.dto.request.AdminUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.CompanyUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.IndividualUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.response.*;
import com.ftbootcamp.eticketauthservice.exception.ETicketException;
import com.ftbootcamp.eticketauthservice.exception.ExceptionMessages;
import com.ftbootcamp.eticketauthservice.exception.UserClientException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserClientService {

    private final UserClient userClient;

    public AdminUserDetailsResponse createUser(AdminUserSaveRequest request) {
        try {
            GenericResponse<AdminUserDetailsResponse> response = userClient.createUser(request);

            if (response.getStatus().equals(GenericResponseConstants.FAILED)) {
                throw new ETicketException(ExceptionMessages.FAILED_TO_CREATE + response.getMessage());
            }
            return response.getData();

        } catch (FeignException e) {
            throw new UserClientException(ExceptionMessages.FAILED_TO_CONNECT_FEIGN_USER);
        }
    }

    public CompanyUserDetailsResponse createUser(CompanyUserSaveRequest request) {
        try {
            GenericResponse<CompanyUserDetailsResponse> response = userClient.createUser(request);

            if (response.getStatus().equals(GenericResponseConstants.FAILED)) {
                throw new ETicketException(ExceptionMessages.FAILED_TO_CREATE + response.getMessage());
            }
            return response.getData();

        } catch (FeignException e) {
            throw new UserClientException(ExceptionMessages.FAILED_TO_CONNECT_FEIGN_USER);
        }
    }

    public IndividualUserDetailsResponse createUser(IndividualUserSaveRequest request) {
        try {
            GenericResponse<IndividualUserDetailsResponse> response = userClient.createUser(request);

            if (response.getStatus().equals(GenericResponseConstants.FAILED)) {
                throw new ETicketException(ExceptionMessages.FAILED_TO_CREATE + response.getMessage());
            }
            return response.getData();

        } catch (FeignException e) {
            throw new UserClientException(ExceptionMessages.FAILED_TO_CONNECT_FEIGN_USER);
        }
    }

    public UserDetailsResponse getUserByEmail(String email) {

        try{
            GenericResponse<UserDetailsResponse> response = userClient.getUserByEmail(email);

            if (response.getData() == null) {
                throw new ETicketException(ExceptionMessages.USER_NOT_FOUND + email);
            }
            return response.getData();

        } catch(FeignException e){
            throw new UserClientException(ExceptionMessages.FAILED_TO_CONNECT_FEIGN_USER);
        }

    }

}
