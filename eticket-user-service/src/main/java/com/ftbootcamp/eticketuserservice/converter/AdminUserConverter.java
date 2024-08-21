package com.ftbootcamp.eticketuserservice.converter;

import com.ftbootcamp.eticketuserservice.dto.response.AdminUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.AdminUserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.concrete.AdminUser;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AdminUserConverter {

    public static AdminUserSummaryResponse toAdminUserSummaryResponse(AdminUser user) {
        return AdminUserSummaryResponse.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .createdDate(user.getCreatedDate())
                .build();

    }

    public static List<AdminUserSummaryResponse> toAdminUserSummaryResponse(List<AdminUser> users) {
        return users.stream()
                .map(AdminUserConverter::toAdminUserSummaryResponse)
                .toList();
    }

    public static AdminUserDetailsResponse toAdminUserDetailsResponse(AdminUser user) {
        return AdminUserDetailsResponse.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .createdDate(user.getCreatedDate())
                .roles(RoleConverter.roleToRoleResponse(user.getRoles()))
                .build();
    }
}