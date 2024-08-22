package com.ftbootcamp.eticketuserservice.converter;

import com.ftbootcamp.eticketuserservice.dto.request.CompanyUserSaveRequest;
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

    public static CompanyUser toUpdatedCompanyUser(CompanyUser user, CompanyUserSaveRequest request) {
        if(request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if(request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if(request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }
        if(request.getCompanyName() != null) {
            user.setCompanyName(request.getCompanyName());
        }
        if(request.getTaxNumber() != null) {
            user.setTaxNumber(request.getTaxNumber());
        }

        return user;
    }

}