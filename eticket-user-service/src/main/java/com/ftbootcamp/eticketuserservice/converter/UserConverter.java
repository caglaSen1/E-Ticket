package com.ftbootcamp.eticketuserservice.converter;

import com.ftbootcamp.eticketuserservice.dto.response.UserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.UserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.abstracts.User;
import com.ftbootcamp.eticketuserservice.entity.concrete.AdminUser;
import com.ftbootcamp.eticketuserservice.entity.concrete.CompanyUser;
import com.ftbootcamp.eticketuserservice.entity.concrete.IndividualUser;
import com.ftbootcamp.eticketuserservice.entity.constant.RoleEntityConstants;
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

    public static UserDetailsResponse toUserDetailsReaponse(User user) {

        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setEmail(user.getEmail());
        userDetailsResponse.setPhoneNumber(user.getPhoneNumber());
        userDetailsResponse.setCreatedDate(user.getCreatedDate());
        userDetailsResponse.setRoles(RoleConverter.roleToRoleResponse(user.getRoles()));

        if (user instanceof IndividualUser individual) {
            userDetailsResponse.setFirstName(individual.getFirstName());
            userDetailsResponse.setLastName(individual.getLastName());
            userDetailsResponse.setBirthDate(individual.getBirthDate());
            userDetailsResponse.setGender(individual.getGender());
            userDetailsResponse.setInstanceOf(RoleEntityConstants.INDIVIDUAL_USER_ROLE_NAME);
        } else if (user instanceof AdminUser admin) {
            userDetailsResponse.setFirstName(admin.getFirstName());
            userDetailsResponse.setLastName(admin.getLastName());
            userDetailsResponse.setGender(admin.getGender());
            userDetailsResponse.setInstanceOf(RoleEntityConstants.ADMIN_USER_ROLE_NAME);
        } else if (user instanceof CompanyUser company) {
            userDetailsResponse.setCompanyName(company.getCompanyName());
            userDetailsResponse.setTaxNumber(company.getTaxNumber());
            userDetailsResponse.setInstanceOf(RoleEntityConstants.CORPORATE_USER_ROLE_NAME);
        }

        return userDetailsResponse;
    }

}
