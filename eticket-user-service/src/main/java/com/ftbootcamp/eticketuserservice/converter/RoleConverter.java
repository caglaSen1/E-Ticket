package com.ftbootcamp.eticketuserservice.converter;

import com.ftbootcamp.eticketuserservice.dto.request.RoleSaveRequest;
import com.ftbootcamp.eticketuserservice.dto.request.RoleUpdateRequest;
import com.ftbootcamp.eticketuserservice.dto.response.RoleResponse;
import com.ftbootcamp.eticketuserservice.entity.Role;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RoleConverter {

    public static Role toEntity(RoleSaveRequest roleSaveRequest) {
        return new Role(roleSaveRequest.getName().toUpperCase());
    }

    public static RoleResponse roleToRoleResponse(Role role) {
        return RoleResponse.builder()
                .name(role.getName())
                .build();
    }

    public static Role toUpdatedRoleEntity(Role role, RoleUpdateRequest roleUpdateRequest) {
        role.setName(roleUpdateRequest.getName().toUpperCase());
        return role;
    }
}
