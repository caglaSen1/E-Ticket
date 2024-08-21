package com.ftbootcamp.eticketuserservice.service;

import com.ftbootcamp.eticketuserservice.converter.CompanyUserConverter;
import com.ftbootcamp.eticketuserservice.dto.request.CompanyUserCreateRequest;
import com.ftbootcamp.eticketuserservice.dto.request.UserBulkStatusChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.UserPasswordChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.UserRoleRequest;
import com.ftbootcamp.eticketuserservice.dto.response.CompanyUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.CompanyUserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.abstracts.User;
import com.ftbootcamp.eticketuserservice.entity.concrete.CompanyUser;
import com.ftbootcamp.eticketuserservice.entity.concrete.Role;
import com.ftbootcamp.eticketuserservice.entity.constant.RoleEntityConstants;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import com.ftbootcamp.eticketuserservice.repository.CompanyUserRepository;
import com.ftbootcamp.eticketuserservice.rules.CompanyUserBusinessRules;
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
public class CompanyUserService {

    private final CompanyUserRepository companyUserRepository;
    private final CompanyUserBusinessRules companyUserBusinessRules;
    private final RoleBusinessRules roleBusinessRules;
    private final RoleService roleService;

    public CompanyUserDetailsResponse createUser(CompanyUserCreateRequest request) {
        companyUserBusinessRules.checkEmailValid(request.getEmail());
        companyUserBusinessRules.checkEmailAlreadyExist(request.getEmail());
        companyUserBusinessRules.checkPasswordValid(request.getPassword());

        // Hash password
        String hashedPassword = hashPassword(request.getPassword());

        // Create User
        CompanyUser createdUser = new CompanyUser(request.getEmail(), request.getPhoneNumber(), hashedPassword,
                request.getCompanyName(), request.getTaxNumber());
        companyUserRepository.save(createdUser);

        // Add default roles to corporate user (USER, CORPORATE_USER)
        Role userRole = roleService.createRoleIfNotExist(RoleEntityConstants.USER_ROLE_NAME);
        Role corporateUserRole = roleService.createRoleIfNotExist(RoleEntityConstants.CORPORATE_USER_ROLE_NAME);
        createdUser.getRoles().add(userRole);
        createdUser.getRoles().add(corporateUserRole);

        companyUserRepository.save(createdUser);

        log.info("Log: User created. request: {}", request);

        return CompanyUserConverter.toCompanyUserDetailsResponse(createdUser);
    }

    public List<CompanyUserSummaryResponse> getAllCompanyUsers() {
        return CompanyUserConverter.toCompanyUserSummaryResponse(companyUserRepository.findAll());
    }

    public CompanyUserDetailsResponse getCompanyUserById(Long id) {
        return CompanyUserConverter.toCompanyUserDetailsResponse(companyUserBusinessRules.checkUserExistById(id));
    }

    public CompanyUserDetailsResponse getCompanyUserByEmail(String email) {
        return CompanyUserConverter.toCompanyUserDetailsResponse(companyUserBusinessRules.checkUserExistByEmail(email));
    }

    public List<CompanyUserSummaryResponse> getCompanyUsersByStatusList(List<StatusType> statusList) {
        if (statusList == null || statusList.isEmpty()) {
            return CompanyUserConverter.toCompanyUserSummaryResponse(companyUserRepository.findAll());
        }

        return CompanyUserConverter.toCompanyUserSummaryResponse(companyUserRepository.findByStatusList(statusList));
    }

    public List<CompanyUserSummaryResponse> getCompanyUsersByTypeList(List<UserType> userTypeList) {
        if (userTypeList == null || userTypeList.isEmpty()) {
            return CompanyUserConverter.toCompanyUserSummaryResponse(companyUserRepository.findAll());
        }

        return CompanyUserConverter.toCompanyUserSummaryResponse(companyUserRepository.findByTypeList(userTypeList));
    }

    public int getHowManyCompanyUsers() {
        return (int) companyUserRepository.count();
    }

    public CompanyUserSummaryResponse changeStatus(String email, StatusType statusType) {
        CompanyUser user = companyUserBusinessRules.checkUserExistByEmail(email);

        user.setStatusType(statusType);
        companyUserRepository.save(user);

        log.info("Log: Company User status changed. email: {}, statusType: {}", email, statusType);

        return CompanyUserConverter.toCompanyUserSummaryResponse(user);
    }

    public List<CompanyUserSummaryResponse> changeCompanyUserStatusBulk(UserBulkStatusChangeRequest request) {
        List<String> emailList = request.getEmailList();
        StatusType statusType = request.getStatusType();

        emailList.forEach(email -> {
            changeStatus(email, statusType);
        });

        log.info("Log: User status changed in bulk. emailList: {}, statusType: {}", emailList, statusType);

        return CompanyUserConverter.toCompanyUserSummaryResponse(companyUserRepository.findByEmailList(emailList));
    }

    public void changePassword(UserPasswordChangeRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        companyUserBusinessRules.checkPasswordValid(password);

        CompanyUser user = companyUserBusinessRules.checkUserExistByEmail(email);

        user.setPassword(password);
        companyUserRepository.save(user);

        log.info("Log: User password changed. email: {}", email);
    }

    public void addRoleToUser(UserRoleRequest request) {
        CompanyUser user = companyUserBusinessRules.checkUserExistByEmail(request.getEmail());
        Role role = roleBusinessRules.checkRoleExistByName(request.getRoleName());

        user.getRoles().add(role);

        companyUserRepository.save(user);
    }

    public void removeRoleFromUser(UserRoleRequest request) {
        CompanyUser user = companyUserBusinessRules.checkUserExistByEmail(request.getEmail());
        Role role = roleBusinessRules.checkRoleExistByName(request.getRoleName());

        roleBusinessRules.checkRoleToRemoveIsDefault(role.getName());

        user.getRoles().remove(role);

        companyUserRepository.save(user);
    }

    public List<String> getUserRoles(String email) {
        CompanyUser user = companyUserBusinessRules.checkUserExistByEmail(email);

        return user.getRoles().stream().map(Role::getName).toList();
    }
}
