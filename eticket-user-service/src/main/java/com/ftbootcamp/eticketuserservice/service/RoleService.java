package com.ftbootcamp.eticketuserservice.service;

import com.ftbootcamp.eticketuserservice.converter.RoleConverter;
import com.ftbootcamp.eticketuserservice.dto.request.RoleSaveRequest;
import com.ftbootcamp.eticketuserservice.dto.request.RoleUpdateRequest;
import com.ftbootcamp.eticketuserservice.dto.response.role.RoleResponse;
import com.ftbootcamp.eticketuserservice.entity.abstracts.User;
import com.ftbootcamp.eticketuserservice.entity.concrete.Role;
import com.ftbootcamp.eticketuserservice.producer.kafka.Log;
import com.ftbootcamp.eticketuserservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.eticketuserservice.repository.RoleRepository;
import com.ftbootcamp.eticketuserservice.repository.UserRepository;
import com.ftbootcamp.eticketuserservice.rules.RoleBusinessRules;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleBusinessRules roleBusinessRules;
    private final UserRepository userRepository;
    private final KafkaProducer kafkaProducer;

    public RoleResponse create(RoleSaveRequest roleSaveRequest) {
        roleBusinessRules.checkRoleIsDefault(roleSaveRequest.getName());
        roleBusinessRules.checkRoleNameAlreadyExist(roleSaveRequest.getName());

        Role role = new Role(roleSaveRequest.getName().toUpperCase());
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
        roleBusinessRules.checkRoleIsDefault(roleUpdateRequest.getName());
        roleBusinessRules.checkRoleNameAlreadyExist(roleUpdateRequest.getName());

        Role roleToUpdate = roleBusinessRules.checkRoleExistById(roleUpdateRequest.getId());
        Role updatedRole = RoleConverter.toUpdatedRoleEntity(roleToUpdate, roleUpdateRequest);

        roleRepository.save(updatedRole);

        kafkaProducer.sendLogMessage(new Log("Role updated. Role id: " + updatedRole.getId()));

        return RoleConverter.roleToRoleResponse(updatedRole);
    }


    @Transactional
    public void deleteRole(Long id) {
        Role role = roleBusinessRules.checkRoleExistById(id);
        roleBusinessRules.checkRoleIsDefault(role.getName());

        List<User> usersWithRole = userRepository.findAllOfRole(role);

        for (User user : usersWithRole) {
            user.getRoles().remove(role);
            userRepository.save(user);
        }

        roleRepository.deleteById(id);  // TODO: Soft delete

        kafkaProducer.sendLogMessage(new Log("Role deleted. Role id: " + id));
    }

    public void add(Role role) {
        roleRepository.save(role);

        kafkaProducer.sendLogMessage(new Log("Role added. Role id: " + role.getId() + " Role name: " + role.getName()));
    }
}
