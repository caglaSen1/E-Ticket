package com.ftbootcamp.eticketuserservice.service;

import com.ftbootcamp.eticketuserservice.converter.RoleConverter;
import com.ftbootcamp.eticketuserservice.dto.request.RoleCreateSaveRequest;
import com.ftbootcamp.eticketuserservice.dto.request.RoleUpdateRequest;
import com.ftbootcamp.eticketuserservice.dto.response.RoleResponse;
import com.ftbootcamp.eticketuserservice.entity.concrete.Role;
import com.ftbootcamp.eticketuserservice.producer.kafka.Log;
import com.ftbootcamp.eticketuserservice.producer.kafka.KafkaProducer;
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
    private final KafkaProducer kafkaProducer;

    public RoleResponse create(RoleCreateSaveRequest roleCreateSaveRequest) {
        roleBusinessRules.checkRoleNameAlreadyExist(roleCreateSaveRequest.getName());

        Role role = new Role(roleCreateSaveRequest.getName().toUpperCase());
        roleRepository.save(role);

        kafkaProducer.sendLogMessage(new Log("Role created. Role id: " + role.getId()));

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

        kafkaProducer.sendLogMessage(new Log("Role updated. Role id: " + updatedRole.getId()));

        return RoleConverter.roleToRoleResponse(updatedRole);
    }

    public void deleteRole(Long id) {
        roleBusinessRules.checkRoleExistById(id);
        roleRepository.deleteById(id);

        kafkaProducer.sendLogMessage(new Log("Role soft deleted. Role id: " + id));
    }

    public Role createRoleIfNotExist(String name) {
        if(roleRepository.findByName(name).isEmpty()) {
            roleRepository.save(new Role(name));
        }

        kafkaProducer.sendLogMessage(new Log("Role added. Role name: " + name));

        return roleRepository.findByName(name).get();
    }

    public void add(Role role) {
        roleRepository.save(role);

        kafkaProducer.sendLogMessage(new Log("Role added. Role id: " + role.getId() + " Role name: " + role.getName()));
    }
}
