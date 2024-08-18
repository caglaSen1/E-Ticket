package com.ftbootcamp.eticketuserservice.converter;

import com.ftbootcamp.eticketuserservice.dto.response.UserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.UserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.User;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UserConverter {

    public static UserSummaryResponse toUserSummaryResponse(User user) {

        return UserSummaryResponse.builder()
                .email(user.getEmail())
                .gender(user.getGender())
                .userType(user.getUserType())
                .statusType(user.getStatusType())
                .createdDate(user.getCreatedDate())
                .build();

    }

    public static List<UserSummaryResponse> toUserSummaryResponse(List<User> users) {

        return users.stream()
                .map(UserConverter::toUserSummaryResponse)
                .toList();
    }

    public static UserDetailsResponse toUserDetailsResponse(User user) {

        return UserDetailsResponse.builder()
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .userType(user.getUserType())
                .statusType(user.getStatusType())
                .roles(RoleConverter.roleToRoleResponse(user.getRoles()))
                .createdDate(user.getCreatedDate())
                .build();
    }

}
