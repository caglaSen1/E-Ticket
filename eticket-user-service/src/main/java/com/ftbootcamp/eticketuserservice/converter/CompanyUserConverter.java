package com.ftbootcamp.eticketuserservice.converter;

import com.ftbootcamp.eticketuserservice.dto.response.CompanyUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.CompanyUserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.concrete.CompanyUser;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CompanyUserConverter {

    public static CompanyUserSummaryResponse toCompanyUserSummaryResponse(CompanyUser user) {
        return CompanyUserSummaryResponse.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .companyName(user.getCompanyName())
                .taxNumber(user.getTaxNumber())
                .userType(user.getUserType())
                .statusType(user.getStatusType())
                .createdDate(user.getCreatedDate())
                .build();
    }

    public static List<CompanyUserSummaryResponse> toCompanyUserSummaryResponse(List<CompanyUser> users) {
        return users.stream()
                .map(CompanyUserConverter::toCompanyUserSummaryResponse)
                .toList();
    }

    public static CompanyUserDetailsResponse toCompanyUserDetailsResponse(CompanyUser user) {
        return CompanyUserDetailsResponse.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .companyName(user.getCompanyName())
                .taxNumber(user.getTaxNumber())
                .userType(user.getUserType())
                .statusType(user.getStatusType())
                .createdDate(user.getCreatedDate())
                .roles(RoleConverter.roleToRoleResponse(user.getRoles()))
                .build();
    }

}