package com.ftbootcamp.eticketuserservice.service;

import com.ftbootcamp.eticketuserservice.converter.UserConverter;
import com.ftbootcamp.eticketuserservice.dto.request.UserBulkStatusChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.UserCreateRequest;
import com.ftbootcamp.eticketuserservice.dto.request.UserPasswordChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.UserRoleRequest;
import com.ftbootcamp.eticketuserservice.dto.response.UserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.UserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.Role;
import com.ftbootcamp.eticketuserservice.entity.User;
import com.ftbootcamp.eticketuserservice.entity.constant.RoleEntityConstants;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import com.ftbootcamp.eticketuserservice.repository.UserRepository;
import com.ftbootcamp.eticketuserservice.rules.RoleBusinessRules;
import com.ftbootcamp.eticketuserservice.rules.UserBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.ftbootcamp.eticketuserservice.core.PasswordHasher.hashPassword;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserBusinessRules userBusinessRules;
    private final RoleBusinessRules roleBusinessRules;
    private final RoleService roleService;

    // TODO: add update method

    public UserDetailsResponse createUser(UserCreateRequest request) {
        userBusinessRules.checkEmailNull(request.getEmail());
        userBusinessRules.checkEmailValid(request.getEmail());
        userBusinessRules.checkEmailAlreadyExist(request.getEmail());
        userBusinessRules.checkPasswordValid(request.getPassword());

        // Hash password
        String hashedPassword = hashPassword(request.getPassword());

        // Create User
        User createdUser = new User(request.getEmail(), hashedPassword);
        userRepository.save(createdUser);

        // Add default role to user
        Role defaultRole = roleService.createDefaultRoleIfNotExist(RoleEntityConstants.DEFAULT_ROLE_NAME);
        createdUser.getRoles().add(defaultRole);
        defaultRole.getUsers().add(createdUser);

        userRepository.save(createdUser);
        roleService.save(defaultRole);

        log.info("Log: User created. request: {}", request);

        return UserConverter.toUserDetailsResponse(createdUser);
    }

    public List<UserSummaryResponse> getAllUsers() {
        return UserConverter.toUserSummaryResponse(userRepository.findAll());
    }

    public UserDetailsResponse getUserById(Long id) {
        userBusinessRules.checkIdNull(id);;
        return UserConverter.toUserDetailsResponse(userBusinessRules.checkUserExistById(id));
    }

    public UserDetailsResponse getUserByEmail(String email) {
        userBusinessRules.checkEmailNull(email);
        return UserConverter.toUserDetailsResponse(userBusinessRules.checkUserExistByEmail(email));
    }

    public List<UserSummaryResponse> getUsersByStatusList(List<StatusType> statusList) {
        if (statusList == null || statusList.isEmpty()) {
            return UserConverter.toUserSummaryResponse(userRepository.findAll());
        }

        return UserConverter.toUserSummaryResponse(userRepository.findByStatusList(statusList));
    }

    public List<UserSummaryResponse> getUsersByTypeList(List<UserType> userTypeList) {
        if (userTypeList == null || userTypeList.isEmpty()) {
            return UserConverter.toUserSummaryResponse(userRepository.findAll());
        }

        return UserConverter.toUserSummaryResponse(userRepository.findByTypeList(userTypeList));
    }

    public int getHowManyUsers() {
        return (int) userRepository.count();
    }

    public UserSummaryResponse changeStatus(String email, StatusType statusType) {
        userBusinessRules.checkEmailNull(email);
        User user = userBusinessRules.checkUserExistByEmail(email);

        user.setStatusType(statusType);
        userRepository.save(user);

        log.info("Log: User status changed. email: {}, statusType: {}", email, statusType);

        return UserConverter.toUserSummaryResponse(user);
    }

    public List<UserSummaryResponse> changeStatusBulk(UserBulkStatusChangeRequest request) {
        List<String> emailList = request.getEmailList();
        StatusType statusType = request.getStatusType();

        emailList.forEach(email -> {
            changeStatus(email, statusType);
        });

        log.info("Log: User status changed in bulk. emailList: {}, statusType: {}", emailList, statusType);

        return UserConverter.toUserSummaryResponse(userRepository.findByEmailList(emailList));
    }

    public void changePassword(UserPasswordChangeRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        userBusinessRules.checkPasswordValid(password);
        userBusinessRules.checkEmailNull(email);

        User user = userBusinessRules.checkUserExistByEmail(email);

        user.setPassword(password);
        userRepository.save(user);

        log.info("Log: User password changed. email: {}", email);
    }

    public void addRoleToUser(UserRoleRequest request) {
        userBusinessRules.checkEmailNull(request.getEmail());
        roleBusinessRules.checkNameNull(request.getRoleName());

        User user = userBusinessRules.checkUserExistByEmail(request.getEmail());
        Role role = roleBusinessRules.checkRoleExistByName(request.getRoleName());

        user.getRoles().add(role);
        role.getUsers().add(user);

        userRepository.save(user);
        roleService.save(role);
    }

    public void removeRoleFromUser(UserRoleRequest request) {
        userBusinessRules.checkEmailNull(request.getEmail());
        roleBusinessRules.checkNameNull(request.getRoleName());
        roleBusinessRules.checkRoleToRemoveIsDefault(request.getRoleName());

        User user = userBusinessRules.checkUserExistByEmail(request.getEmail());
        Role role = roleBusinessRules.checkRoleExistByName(request.getRoleName());

        user.getRoles().remove(role);
        role.getUsers().remove(user);

        userRepository.save(user);
        roleService.save(role);
    }

    public List<String> getUserRoles(String email) {
        userBusinessRules.checkEmailNull(email);
        User user = userBusinessRules.checkUserExistByEmail(email);

        return user.getRoles().stream().map(Role::getName).toList();
    }

}
