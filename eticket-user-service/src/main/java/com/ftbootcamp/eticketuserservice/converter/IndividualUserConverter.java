package com.ftbootcamp.eticketuserservice.converter;

import com.ftbootcamp.eticketuserservice.dto.response.IndividualUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.IndividualUserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.concrete.IndividualUser;

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
                .createdDate(user.getCreatedDate())
                .build();
    }

    public static List<IndividualUserSummaryResponse> toIndividualUserSummaryResponse(List<IndividualUser> users) {
        return users.stream()
                .map(IndividualUserConverter::toIndividualUserSummaryResponse)
                .toList();
    }

    public static IndividualUserDetailsResponse toIndividualUserDetailsResponse(IndividualUser user) {
        return IndividualUserDetailsResponse.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .createdDate(user.getCreatedDate())
                .roles(RoleConverter.roleToRoleResponse(user.getRoles()))
                .build();
    }
}

