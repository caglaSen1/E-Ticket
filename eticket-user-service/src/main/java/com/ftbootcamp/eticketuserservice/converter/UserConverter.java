package com.ftbootcamp.eticketuserservice.converter;

import com.ftbootcamp.eticketuserservice.dto.response.UserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.UserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.abstracts.User;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UserConverter {

    public static UserSummaryResponse toUserSummaryResponse(User user) {

        return UserSummaryResponse.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
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
                .phoneNumber(user.getPhoneNumber())
                .createdDate(user.getCreatedDate())
                .roles(RoleConverter.roleToRoleResponse(user.getRoles()))
                .build();
    }

}
