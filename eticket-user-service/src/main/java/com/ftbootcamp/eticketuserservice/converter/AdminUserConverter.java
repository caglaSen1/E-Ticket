package com.ftbootcamp.eticketuserservice.converter;

import com.ftbootcamp.eticketuserservice.dto.request.AdminUserSaveRequest;
import com.ftbootcamp.eticketuserservice.dto.response.admin.AdminUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.admin.AdminUserPaginatedResponse;
import com.ftbootcamp.eticketuserservice.dto.response.admin.AdminUserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.concrete.AdminUser;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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

    public static AdminUserPaginatedResponse toAdminUserPaginatedResponse(Page<AdminUser> users) {
        return AdminUserPaginatedResponse.builder()
                .userSummaryResponses(toAdminUserSummaryResponse(users.getContent()))
                .currentPage(users.getNumber())
                .numberOfElementsInCurrentPage(users.getNumberOfElements())
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .build();
    }

    public static AdminUser toUpdatedAdminUser(AdminUser user, AdminUserSaveRequest request) {
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