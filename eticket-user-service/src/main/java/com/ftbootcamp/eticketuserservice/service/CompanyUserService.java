package com.ftbootcamp.eticketuserservice.service;

import com.ftbootcamp.eticketuserservice.converter.CompanyUserConverter;
import com.ftbootcamp.eticketuserservice.dto.request.*;
import com.ftbootcamp.eticketuserservice.dto.request.user.UserBulkStatusChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.user.UserPasswordChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.user.UserRoleRequest;
import com.ftbootcamp.eticketuserservice.dto.response.company.CompanyUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.company.CompanyUserPaginatedResponse;
import com.ftbootcamp.eticketuserservice.dto.response.company.CompanyUserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.concrete.CompanyUser;
import com.ftbootcamp.eticketuserservice.entity.concrete.Role;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import com.ftbootcamp.eticketuserservice.producer.kafka.Log;
import com.ftbootcamp.eticketuserservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.eticketuserservice.repository.CompanyUserRepository;
import com.ftbootcamp.eticketuserservice.rules.CompanyUserBusinessRules;
import com.ftbootcamp.eticketuserservice.rules.RoleBusinessRules;
import com.ftbootcamp.eticketuserservice.util.ExtractFromToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyUserService {

    private final CompanyUserRepository companyUserRepository;
    private final CompanyUserBusinessRules companyUserBusinessRules;
    private final RoleBusinessRules roleBusinessRules;
    private final KafkaProducer kafkaProducer;

    public List<CompanyUserSummaryResponse> getAllCompanyUsers() {
        return CompanyUserConverter.toCompanyUserSummaryResponse(companyUserRepository.findAll());
    }

    public CompanyUserDetailsResponse getCompanyUserByToken(String token) {
        String email = ExtractFromToken.email(token);
        return CompanyUserConverter.toCompanyUserDetailsResponse(companyUserBusinessRules.checkUserExistByEmail(email));
    }

    public CompanyUserDetailsResponse getCompanyUserByEmail(String email) {
        return CompanyUserConverter.toCompanyUserDetailsResponse(companyUserBusinessRules.checkUserExistByEmail(email));
    }

    public CompanyUserPaginatedResponse getCompanyUsersByStatusList(int page, int size, List<StatusType> statusList) {
        PageRequest pageRequest = PageRequest.of(page, size);

        if (statusList == null || statusList.isEmpty()) {
            return CompanyUserConverter.toCompanyUserPaginatedResponse(companyUserRepository.findAll(pageRequest));
        }

        return CompanyUserConverter.toCompanyUserPaginatedResponse(companyUserRepository
                .findByStatusList(statusList, pageRequest));
    }

    public CompanyUserPaginatedResponse getCompanyUsersByTypeList(int page, int size, List<UserType> userTypeList) {
        PageRequest pageRequest = PageRequest.of(page, size);

        if (userTypeList == null || userTypeList.isEmpty()) {
            return CompanyUserConverter.toCompanyUserPaginatedResponse(companyUserRepository.findAll(pageRequest));
        }

        return CompanyUserConverter.toCompanyUserPaginatedResponse(companyUserRepository
                .findByTypeList(userTypeList, pageRequest));
    }

    public int getHowManyCompanyUsers() {
        return (int) companyUserRepository.count();
    }

    public CompanyUserSummaryResponse changeStatus(String email, StatusType statusType) {
        CompanyUser user = companyUserBusinessRules.checkUserExistByEmail(email);

        user.setStatusType(statusType);
        companyUserRepository.save(user);

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Company User status changed. Company User email: " + email +
                ", status: " + statusType));

        return CompanyUserConverter.toCompanyUserSummaryResponse(user);
    }

    public CompanyUserPaginatedResponse changeCompanyUserStatusBulk(UserBulkStatusChangeRequest request) {

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());

        List<String> emailList = request.getEmailList();
        StatusType statusType = request.getStatusType();

        emailList.forEach(email -> {
            changeStatus(email, statusType);
        });

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Company Users status changed. Email list: " + emailList +
                ", status: " + statusType));

        return CompanyUserConverter.toCompanyUserPaginatedResponse(companyUserRepository
                .findByEmailList(emailList, pageRequest));
    }

    public CompanyUserDetailsResponse updateUser(CompanyUserSaveRequest request, String token) {
        String email = ExtractFromToken.email(token);

        CompanyUser userToUpdate = companyUserBusinessRules.checkUserExistByEmail(email);
        companyUserBusinessRules.checkEmailAlreadyExist(request.getEmail());
        companyUserBusinessRules.checkPasswordValid(request.getPassword());

        CompanyUser updatedUser = CompanyUserConverter.toUpdatedCompanyUser(userToUpdate, request);
        companyUserRepository.save(updatedUser);

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Company User updated. Company User id: " + updatedUser.getId()));

        return CompanyUserConverter.toCompanyUserDetailsResponse(updatedUser);
    }

    public void changePassword(UserPasswordChangeRequest request, String token) {
        String email = ExtractFromToken.email(token);
        String password = request.getPassword();

        CompanyUser user = companyUserBusinessRules.checkUserExistByEmail(email);
        companyUserBusinessRules.checkPasswordValid(password);

        user.setPassword(password);
        companyUserRepository.save(user);

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Company user changed password. User id: " + user.getId()));
    }

    public void addRoleToUser(UserRoleRequest request) {
        CompanyUser user = companyUserBusinessRules.checkUserExistByEmail(request.getEmail());
        Role role = roleBusinessRules.checkRoleExistByName(request.getRoleName());

        roleBusinessRules.checkRoleToAddOrRemoveIsDefault(role.getName());

        user.getRoles().add(role);

        companyUserRepository.save(user);

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Role added to company user. User id: " +
                user.getId() + " Role name: " + role.getName()));
    }

    public void removeRoleFromUser(UserRoleRequest request) {
        CompanyUser user = companyUserBusinessRules.checkUserExistByEmail(request.getEmail());
        Role role = roleBusinessRules.checkRoleExistByName(request.getRoleName());

        roleBusinessRules.checkRoleToAddOrRemoveIsDefault(role.getName());

        user.getRoles().remove(role);

        companyUserRepository.save(user);

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Role removed from company user. User id: " +
                user.getId() + " Role name: " + role.getName()));
    }

    public List<String> getUserRoles(String email) {
        CompanyUser user = companyUserBusinessRules.checkUserExistByEmail(email);

        return user.getRoles().stream().map(Role::getName).toList();
    }
}
