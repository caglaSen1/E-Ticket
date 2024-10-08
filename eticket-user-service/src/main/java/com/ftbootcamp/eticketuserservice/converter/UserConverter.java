package com.ftbootcamp.eticketuserservice.converter;

import com.ftbootcamp.eticketuserservice.dto.response.user.UserSummaryPaginatedResponse;
import com.ftbootcamp.eticketuserservice.dto.response.user.UserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.user.UserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.abstracts.User;
import com.ftbootcamp.eticketuserservice.entity.concrete.AdminUser;
import com.ftbootcamp.eticketuserservice.entity.concrete.CompanyUser;
import com.ftbootcamp.eticketuserservice.entity.concrete.IndividualUser;
import com.ftbootcamp.eticketuserservice.entity.constant.RoleEntityConstants;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

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

    public static UserSummaryPaginatedResponse toPaginatedUserSummaryResponse(Page<User> users) {

        return UserSummaryPaginatedResponse.builder()
                .userSummaryResponses(toUserSummaryResponse(users.getContent()))
                .currentPage(users.getNumber())
                .numberOfElementsInCurrentPage(users.getNumberOfElements())
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .build();
    }

    public static UserDetailsResponse toUserDetailsResponse(User user) {

        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setEmail(user.getEmail());
        userDetailsResponse.setPhoneNumber(user.getPhoneNumber());
        userDetailsResponse.setCreatedDate(user.getCreatedDate());
        userDetailsResponse.setRoles(RoleConverter.roleToRoleResponse(user.getRoles()));

        if (user instanceof IndividualUser individual) {
            userDetailsResponse.setFirstName(individual.getFirstName());
            userDetailsResponse.setLastName(individual.getLastName());
            userDetailsResponse.setBirthDate(individual.getBirthDate());
            userDetailsResponse.setUserType(individual.getUserType());
            userDetailsResponse.setStatusType(individual.getStatusType());
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
            userDetailsResponse.setUserType(company.getUserType());
            userDetailsResponse.setStatusType(company.getStatusType());
            userDetailsResponse.setInstanceOf(RoleEntityConstants.COMPANY_USER_ROLE_NAME);
        }

        return userDetailsResponse;
    }
}
