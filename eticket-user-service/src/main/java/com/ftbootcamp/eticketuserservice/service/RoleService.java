package com.ftbootcamp.eticketuserservice.service;

import com.ftbootcamp.eticketuserservice.converter.RoleConverter;
import com.ftbootcamp.eticketuserservice.dto.request.RoleSaveRequest;
import com.ftbootcamp.eticketuserservice.dto.request.RoleUpdateRequest;
import com.ftbootcamp.eticketuserservice.dto.response.RoleResponse;
import com.ftbootcamp.eticketuserservice.entity.Role;
import com.ftbootcamp.eticketuserservice.repository.RoleRepository;
import com.ftbootcamp.eticketuserservice.rules.RoleBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleBusinessRules roleBusinessRules;

    public RoleResponse create(RoleSaveRequest roleSaveRequest) {
        roleBusinessRules.checkNameNull(roleSaveRequest.getName(), "");
        roleBusinessRules.checkRoleAlreadyExistByName(roleSaveRequest.getName(),
                "Name: " + roleSaveRequest.getName());

        Role role = RoleConverter.toEntity(roleSaveRequest);
        roleRepository.save(role);

        return RoleConverter.roleToRoleResponse(role);
    }

    public RoleResponse getRoleById(Long id) {
        roleBusinessRules.checkRoleExistById(id, "Id: " + id);

        Role role = roleRepository.findById(id).get();

        return RoleConverter.roleToRoleResponse(role);
    }

    public RoleResponse getRoleByName(String name) {
        roleBusinessRules.checkNameNull(name, "");
        roleBusinessRules.checkRoleExistByName(name, "Name: " + name);

        Role role = roleRepository.findByName(name.toUpperCase()).get();

        return RoleConverter.roleToRoleResponse(role);
    }

    public RoleResponse updateRole(RoleUpdateRequest roleUpdateRequest) {
        roleBusinessRules.checkRoleExistById(roleUpdateRequest.getId(),
                "Id: " + roleUpdateRequest.getId());
        roleBusinessRules.checkRoleAlreadyExistByName(roleUpdateRequest.getName(),
                "Name: " + roleUpdateRequest.getName());

        Role roleToUpdate = roleRepository.findById(roleUpdateRequest.getId()).get();
        Role updatedRole = RoleConverter.toUpdatedRoleEntity(roleToUpdate, roleUpdateRequest);

        roleRepository.save(updatedRole);

        return RoleConverter.roleToRoleResponse(updatedRole);
    }

    public void deleteRole(Long id) {
        roleBusinessRules.checkRoleExistById(id, "Id: " + id);
        roleRepository.deleteById(id);
    }
}
