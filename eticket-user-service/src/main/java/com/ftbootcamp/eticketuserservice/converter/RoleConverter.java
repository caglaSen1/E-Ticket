package com.ftbootcamp.eticketuserservice.converter;

import com.ftbootcamp.eticketuserservice.dto.request.RoleUpdateRequest;
import com.ftbootcamp.eticketuserservice.dto.response.role.RoleResponse;
import com.ftbootcamp.eticketuserservice.entity.concrete.Role;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RoleConverter {

    public static RoleResponse roleToRoleResponse(Role role) {
        return RoleResponse.builder()
                .name(role.getName())
                .build();
    }

    public static List<RoleResponse> roleToRoleResponse(Set<Role> role) {
        return role.stream()
                .map(RoleConverter::roleToRoleResponse)
                .toList();
    }

    public static Role toUpdatedRoleEntity(Role role, RoleUpdateRequest roleUpdateRequest) {
        if(roleUpdateRequest.getName() != null){
            role.setName(roleUpdateRequest.getName().toUpperCase());
        }

        return role;
    }
}
