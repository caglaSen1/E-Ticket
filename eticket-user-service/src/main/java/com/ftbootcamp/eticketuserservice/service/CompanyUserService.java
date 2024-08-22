package com.ftbootcamp.eticketuserservice.service;

import com.ftbootcamp.eticketuserservice.converter.CompanyUserConverter;
import com.ftbootcamp.eticketuserservice.dto.request.*;
import com.ftbootcamp.eticketuserservice.dto.response.CompanyUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.CompanyUserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.concrete.CompanyUser;
import com.ftbootcamp.eticketuserservice.entity.concrete.Role;
import com.ftbootcamp.eticketuserservice.entity.constant.RoleEntityConstants;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import com.ftbootcamp.eticketuserservice.producer.kafka.Log;
import com.ftbootcamp.eticketuserservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.eticketuserservice.producer.rabbitmq.RabbitMqProducer;
import com.ftbootcamp.eticketuserservice.producer.rabbitmq.dto.NotificationSendRequest;
import com.ftbootcamp.eticketuserservice.producer.rabbitmq.enums.NotificationType;
import com.ftbootcamp.eticketuserservice.repository.CompanyUserRepository;
import com.ftbootcamp.eticketuserservice.rules.CompanyUserBusinessRules;
import com.ftbootcamp.eticketuserservice.rules.RoleBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final RabbitMqProducer rabbitMqProducer;
    private final KafkaProducer kafkaProducer;

    public CompanyUserDetailsResponse createUser(CompanyUserSaveRequest request) {
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

        // Send message to user with RabbitMQ Service (Asencronize):
        String infoMessage = "Welcome to our system. Your account created successfully.";
        List<NotificationType> notificationTypes = new ArrayList<>();
        notificationTypes.add(NotificationType.EMAIL);
        if (createdUser.getPhoneNumber() != null) {
            notificationTypes.add(NotificationType.SMS);
        }
        rabbitMqProducer.sendMessage(new NotificationSendRequest(notificationTypes, createdUser.getEmail(),
                createdUser.getPhoneNumber(), infoMessage));

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Company User created. Company User id: " + createdUser.getId()));

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

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Company User status changed. Company User email: " + email +
                ", status: " + statusType));

        return CompanyUserConverter.toCompanyUserSummaryResponse(user);
    }

    public List<CompanyUserSummaryResponse> changeCompanyUserStatusBulk(UserBulkStatusChangeRequest request) {
        List<String> emailList = request.getEmailList();
        StatusType statusType = request.getStatusType();

        emailList.forEach(email -> {
            changeStatus(email, statusType);
        });

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Company Users status changed. Email list: " + emailList +
                ", status: " + statusType));

        return CompanyUserConverter.toCompanyUserSummaryResponse(companyUserRepository.findByEmailList(emailList));
    }

    public CompanyUserDetailsResponse updateUser(CompanyUserSaveRequest request) {
        CompanyUser userToUpdate = companyUserBusinessRules.checkUserExistByEmail(request.getEmail());

        CompanyUser updatedUser = CompanyUserConverter.toUpdatedCompanyUser(userToUpdate, request);
        companyUserRepository.save(updatedUser);

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Company User updated. Company User id: " + updatedUser.getId()));

        return CompanyUserConverter.toCompanyUserDetailsResponse(updatedUser);
    }

    public void changePassword(UserPasswordChangeRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        companyUserBusinessRules.checkPasswordValid(password);

        CompanyUser user = companyUserBusinessRules.checkUserExistByEmail(email);

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
