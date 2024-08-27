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
            throw new ETicketException(ExceptionMessages.ROLE_NOT_FOUND + " Id: " + id);
        }

        return roleRepository.findById(id).get();
    }

    public Role checkRoleExistByName(String name) {
        if (roleRepository.findByName(name.toUpperCase()).isEmpty()) {
            throw new ETicketException(ExceptionMessages.ROLE_NOT_FOUND + " Name: " + name);
        }

        return roleRepository.findByName(name.toUpperCase()).get();
    }

    public void checkRoleNameAlreadyExist(String name) {
        if (roleRepository.findByName(name.toUpperCase()).isPresent()) {
            throw new ETicketException(ExceptionMessages.ROLE_ALREADY_EXIST + " Name: " + name);
        }
    }

    public void checkRoleIsDefault(String roleName) {
        if (roleName.equals(RoleEntityConstants.USER_ROLE_NAME) ||
                roleName.equals(RoleEntityConstants.ADMIN_USER_ROLE_NAME) ||
                roleName.equals(RoleEntityConstants.INDIVIDUAL_USER_ROLE_NAME) ||
                roleName.equals(RoleEntityConstants.COMPANY_USER_ROLE_NAME)) {
            throw new ETicketException(ExceptionMessages.DEFAULT_ROLE_CANNOT_BE_ADDED_REMOVED +
                    " Role: " + roleName);
        }
    }
}
