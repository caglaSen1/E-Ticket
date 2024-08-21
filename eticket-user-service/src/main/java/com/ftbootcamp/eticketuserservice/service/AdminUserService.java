package com.ftbootcamp.eticketuserservice.service;

import com.ftbootcamp.eticketuserservice.converter.AdminUserConverter;
import com.ftbootcamp.eticketuserservice.dto.request.AdminUserCreateRequest;
import com.ftbootcamp.eticketuserservice.dto.request.UserPasswordChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.UserRoleRequest;
import com.ftbootcamp.eticketuserservice.dto.response.AdminUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.AdminUserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.abstracts.User;
import com.ftbootcamp.eticketuserservice.entity.concrete.AdminUser;
import com.ftbootcamp.eticketuserservice.entity.concrete.Role;
import com.ftbootcamp.eticketuserservice.entity.constant.RoleEntityConstants;
import com.ftbootcamp.eticketuserservice.repository.AdminUserRepository;
import com.ftbootcamp.eticketuserservice.rules.AdminUserBusinessRules;
import com.ftbootcamp.eticketuserservice.rules.RoleBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ftbootcamp.eticketuserservice.core.PasswordHasher.hashPassword;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final AdminUserBusinessRules adminUserBusinessRules;
    private final RoleBusinessRules roleBusinessRules;
    private final RoleService roleService;

    public AdminUserDetailsResponse createUser(AdminUserCreateRequest request) {
        adminUserBusinessRules.checkEmailValid(request.getEmail());
        adminUserBusinessRules.checkEmailAlreadyExist(request.getEmail());
        adminUserBusinessRules.checkPasswordValid(request.getPassword());

        // Hash password
        String hashedPassword = hashPassword(request.getPassword());

        // Create User
        AdminUser createdUser = new AdminUser(request.getEmail(), request.getPhoneNumber(), hashedPassword,
                request.getFirstName(), request.getLastName(), request.getNationalId(), request.getGender());
        adminUserRepository.save(createdUser);

        // Add default role to admin user (USER, ADMIN_USER)
        Role userRole = roleService.createRoleIfNotExist(RoleEntityConstants.USER_ROLE_NAME);
        Role adminUserRole = roleService.createRoleIfNotExist(RoleEntityConstants.ADMIN_USER_ROLE_NAME);
        createdUser.getRoles().add(userRole);
        createdUser.getRoles().add(adminUserRole);

        adminUserRepository.save(createdUser);

        log.info("Log: User created. request: {}", request);

        return AdminUserConverter.toAdminUserDetailsResponse(createdUser);
    }

    public List<AdminUserSummaryResponse> getAllUsers() {
        return AdminUserConverter.toAdminUserSummaryResponse(adminUserRepository.findAll());
    }

    public AdminUserDetailsResponse getUserById(Long id) {
        return AdminUserConverter.toAdminUserDetailsResponse(adminUserBusinessRules.checkUserExistById(id));
    }

    public AdminUserDetailsResponse getUserByEmail(String email) {
        return AdminUserConverter.toAdminUserDetailsResponse(adminUserBusinessRules.checkUserExistByEmail(email));
    }

    public int getHowManyUsers() {
        return (int) adminUserRepository.count();
    }

    public void changePassword(UserPasswordChangeRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        adminUserBusinessRules.checkPasswordValid(password);

        AdminUser user = adminUserBusinessRules.checkUserExistByEmail(email);

        user.setPassword(password);
        adminUserRepository.save(user);

        log.info("Log: User password changed. email: {}", email);
    }

    public void addRoleToUser(UserRoleRequest request) {
        AdminUser user = adminUserBusinessRules.checkUserExistByEmail(request.getEmail());
        Role role = roleBusinessRules.checkRoleExistByName(request.getRoleName());

        user.getRoles().add(role);

        adminUserRepository.save(user);
    }

    public void removeRoleFromUser(UserRoleRequest request) {
        AdminUser user = adminUserBusinessRules.checkUserExistByEmail(request.getEmail());
        Role role = roleBusinessRules.checkRoleExistByName(request.getRoleName());

        roleBusinessRules.checkRoleToRemoveIsDefault(role.getName());

        user.getRoles().remove(role);

        adminUserRepository.save(user);
    }

    public List<String> getUserRoles(String email) {
        AdminUser user = adminUserBusinessRules.checkUserExistByEmail(email);

        return user.getRoles().stream().map(Role::getName).toList();
    }

}
