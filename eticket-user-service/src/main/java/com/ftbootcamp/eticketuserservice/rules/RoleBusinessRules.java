package com.ftbootcamp.eticketuserservice.rules;

import com.ftbootcamp.eticketuserservice.entity.concrete.Role;
import com.ftbootcamp.eticketuserservice.entity.constant.RoleEntityConstants;
import com.ftbootcamp.eticketuserservice.exception.ETicketException;
import com.ftbootcamp.eticketuserservice.exception.ExceptionMessages;
import com.ftbootcamp.eticketuserservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleBusinessRules {

    private final RoleRepository roleRepository;

    public Role checkRoleExistById(Long id) {
        if (roleRepository.findById(id).isEmpty()) {
            handleException(ExceptionMessages.ROLE_NOT_FOUND, "Id: " + id);
        }

        return roleRepository.findById(id).get();
    }

    public Role checkRoleExistByName(String name) {
        if (roleRepository.findByName(name.toUpperCase()).isEmpty()) {
            handleException(ExceptionMessages.ROLE_NOT_FOUND, "Name: " + name);
        }

        return roleRepository.findByName(name.toUpperCase()).get();
    }

    public void checkRoleNameAlreadyExist(String name) {
        if (roleRepository.findByName(name.toUpperCase()).isPresent()) {
            handleException(ExceptionMessages.ROLE_ALREADY_EXIST, "Name: " + name);
        }
    }

    public void checkRoleToRemoveIsDefault(String roleToRemove) {

        if(roleToRemove.equals(RoleEntityConstants.USER_ROLE_NAME) ||
                roleToRemove.equals(RoleEntityConstants.ADMIN_USER_ROLE_NAME) ||
                roleToRemove.equals(RoleEntityConstants.INDIVIDUAL_USER_ROLE_NAME) ||
                roleToRemove.equals(RoleEntityConstants.CORPORATE_USER_ROLE_NAME)){
            handleException(ExceptionMessages.DEFAULT_ROLE_CANNOT_BE_REMOVED, "Role: " + roleToRemove);
        }
    }

    private void handleException(String exceptionMessage, String request) {
        String logMessage;

        if (request != null && !request.isEmpty()) {
            logMessage = String.format("Log: Error: %s, Request: %s", exceptionMessage, request);
        } else {
            logMessage = String.format("Log: Error: %s", exceptionMessage);
        }

        throw new ETicketException(exceptionMessage, logMessage);
    }
}
