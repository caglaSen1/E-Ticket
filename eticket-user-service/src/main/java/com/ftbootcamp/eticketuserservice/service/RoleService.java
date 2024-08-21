package com.ftbootcamp.eticketuserservice.service;

import com.ftbootcamp.eticketuserservice.converter.RoleConverter;
import com.ftbootcamp.eticketuserservice.dto.request.RoleCreateRequest;
import com.ftbootcamp.eticketuserservice.dto.request.RoleUpdateRequest;
import com.ftbootcamp.eticketuserservice.dto.response.RoleResponse;
import com.ftbootcamp.eticketuserservice.entity.concrete.Role;
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

    public RoleResponse create(RoleCreateRequest roleCreateRequest) {
        roleBusinessRules.checkRoleNameAlreadyExist(roleCreateRequest.getName());

        Role role = new Role(roleCreateRequest.getName().toUpperCase());
        roleRepository.save(role);

        return RoleConverter.roleToRoleResponse(role);
    }

    public RoleResponse getRoleById(Long id) {
        return RoleConverter.roleToRoleResponse(roleBusinessRules.checkRoleExistById(id));
    }

    public RoleResponse getRoleByName(String name) {
        return RoleConverter.roleToRoleResponse(roleBusinessRules.checkRoleExistByName(name));
    }

    public RoleResponse updateRole(RoleUpdateRequest roleUpdateRequest) {
        roleBusinessRules.checkRoleNameAlreadyExist(roleUpdateRequest.getName());

        Role roleToUpdate = roleBusinessRules.checkRoleExistById(roleUpdateRequest.getId());
        Role updatedRole = RoleConverter.toUpdatedRoleEntity(roleToUpdate, roleUpdateRequest);

        roleRepository.save(updatedRole);

        return RoleConverter.roleToRoleResponse(updatedRole);
    }

    public void deleteRole(Long id) {
        roleBusinessRules.checkRoleExistById(id);
        roleRepository.deleteById(id);
    }

    public Role createRoleIfNotExist(String name) {
        if(roleRepository.findByName(name).isEmpty()) {
            roleRepository.save(new Role(name));
        }

        return roleRepository.findByName(name).get();
    }

    public void add(Role role) {
        roleRepository.save(role);
    }
}
