package com.ftbootcamp.eticketuserservice.converter;

import com.ftbootcamp.eticketuserservice.dto.request.IndividualUserUpdateRequest;
import com.ftbootcamp.eticketuserservice.dto.response.individual.IndividualUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.individual.IndividualUserPaginatedResponse;
import com.ftbootcamp.eticketuserservice.dto.response.individual.IndividualUserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.concrete.IndividualUser;
import org.springframework.data.domain.Page;

import java.util.List;

public class IndividualUserConverter {

    public static IndividualUserSummaryResponse toIndividualUserSummaryResponse(IndividualUser user) {
        return IndividualUserSummaryResponse.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .userType(user.getUserType())
                .statusType(user.getStatusType())
                .createdDate(user.getCreatedDate())
                .build();
    }

    public static List<IndividualUserSummaryResponse> toIndividualUserSummaryResponse(List<IndividualUser> users) {
        return users.stream()
                .map(IndividualUserConverter::toIndividualUserSummaryResponse)
                .toList();
    }

    public static IndividualUserPaginatedResponse toIndividualUserPaginatedResponse(Page<IndividualUser> users) {
        return IndividualUserPaginatedResponse.builder()
                .userSummaryResponses(toIndividualUserSummaryResponse(users.getContent()))
                .currentPage(users.getNumber())
                .numberOfElementsInCurrentPage(users.getNumberOfElements())
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .build();
    }

    public static IndividualUserDetailsResponse toIndividualUserDetailsResponse(IndividualUser user) {
        return IndividualUserDetailsResponse.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .userType(user.getUserType())
                .statusType(user.getStatusType())
                .createdDate(user.getCreatedDate())
                .roles(RoleConverter.roleToRoleResponse(user.getRoles()))
                .build();
    }

    public static IndividualUser toUpdatedIndividualUser(IndividualUser user, IndividualUserUpdateRequest request) {
        if(request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if(request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if(request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }
        if(request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if(request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if(request.getNationalId() != 0) {
            user.setNationalId(request.getNationalId());
        }
        if(request.getGender() != null) {
            user.setGender(request.getGender());
        }
        return user;
    }
}

