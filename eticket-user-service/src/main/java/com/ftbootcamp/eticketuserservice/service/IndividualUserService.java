package com.ftbootcamp.eticketuserservice.service;

import com.ftbootcamp.eticketuserservice.converter.IndividualUserConverter;
import com.ftbootcamp.eticketuserservice.dto.request.*;
import com.ftbootcamp.eticketuserservice.dto.response.IndividualUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.IndividualUserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.concrete.IndividualUser;
import com.ftbootcamp.eticketuserservice.entity.concrete.Role;
import com.ftbootcamp.eticketuserservice.entity.constant.RoleEntityConstants;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import com.ftbootcamp.eticketuserservice.producer.Log;
import com.ftbootcamp.eticketuserservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.eticketuserservice.producer.rabbitmq.RabbitMqProducer;
import com.ftbootcamp.eticketuserservice.producer.rabbitmq.dto.NotificationSendRequest;
import com.ftbootcamp.eticketuserservice.producer.rabbitmq.enums.NotificationType;
import com.ftbootcamp.eticketuserservice.repository.IndividualUserRepository;
import com.ftbootcamp.eticketuserservice.rules.IndividualUserBusinessRules;
import com.ftbootcamp.eticketuserservice.rules.RoleBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ftbootcamp.eticketuserservice.core.PasswordHasher.hashPassword;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndividualUserService {

    private final IndividualUserRepository individualUserRepository;
    private final IndividualUserBusinessRules individualUserBusinessRules;
    private final RoleBusinessRules roleBusinessRules;
    private final RoleService roleService;
    private final RabbitMqProducer rabbitMqProducer;
    private final KafkaProducer kafkaProducer;

    public IndividualUserDetailsResponse createUser(IndividualUserSaveRequest request) {
        individualUserBusinessRules.checkEmailValid(request.getEmail());
        individualUserBusinessRules.checkEmailAlreadyExist(request.getEmail());
        individualUserBusinessRules.checkPasswordValid(request.getPassword());

        // Hash password
        String hashedPassword = hashPassword(request.getPassword());

        // Create User
        IndividualUser createdUser = new IndividualUser(request.getEmail(), request.getPhoneNumber(), hashedPassword,
                request.getFirstName(), request.getLastName(), request.getNationalId(), request.getBirthDate(),
                request.getGender());
        individualUserRepository.save(createdUser);

        // Add default role to individual user (USER, INDIVIDUAL_USER)
        Role defaultRole = roleService.createRoleIfNotExist(RoleEntityConstants.USER_ROLE_NAME);
        Role individualUserRole = roleService.createRoleIfNotExist(RoleEntityConstants.INDIVIDUAL_USER_ROLE_NAME);
        createdUser.getRoles().add(defaultRole);
        createdUser.getRoles().add(individualUserRole);

        individualUserRepository.save(createdUser);

        // Send message to user with RabbitMQ Service (Asencronize):
        String infoMessage = "Welcome to our system. Your account created successfully.";
        rabbitMqProducer.sendMessage(new NotificationSendRequest(NotificationType.EMAIL, createdUser.getEmail(),
                infoMessage));

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Individual User created. User id: " + createdUser.getId()));

        return IndividualUserConverter.toIndividualUserDetailsResponse(createdUser);
    }

    public List<IndividualUserSummaryResponse> getAllIndividualUsers() {
        return IndividualUserConverter.toIndividualUserSummaryResponse(individualUserRepository.findAll());
    }

    public IndividualUserDetailsResponse getIndividualUserById(Long id) {
        return IndividualUserConverter.toIndividualUserDetailsResponse(
                individualUserBusinessRules.checkUserExistById(id));
    }

    public IndividualUserDetailsResponse getIndividualUserByEmail(String email) {
        return IndividualUserConverter.toIndividualUserDetailsResponse(
                individualUserBusinessRules.checkUserExistByEmail(email));
    }

    public List<IndividualUserSummaryResponse> getIndividualUsersByStatusList(List<StatusType> statusList) {
        if (statusList == null || statusList.isEmpty()) {
            return IndividualUserConverter.toIndividualUserSummaryResponse(individualUserRepository.findAll());
        }

        return IndividualUserConverter.toIndividualUserSummaryResponse(
                individualUserRepository.findByStatusList(statusList));
    }

    public List<IndividualUserSummaryResponse> getIndividualUsersByTypeList(List<UserType> userTypeList) {
        if (userTypeList == null || userTypeList.isEmpty()) {
            return IndividualUserConverter.toIndividualUserSummaryResponse(individualUserRepository.findAll());
        }

        return IndividualUserConverter.toIndividualUserSummaryResponse(individualUserRepository.findByTypeList(userTypeList));
    }

    public int getHowManyIndividualUsers() {
        return (int) individualUserRepository.count();
    }

    public IndividualUserSummaryResponse changeIndividualUserStatus(String email, StatusType statusType) {
        IndividualUser user = individualUserBusinessRules.checkUserExistByEmail(email);

        user.setStatusType(statusType);
        individualUserRepository.save(user);

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Individual User status changed. Individual User email: " + email +
                ", status: " + statusType));

        return IndividualUserConverter.toIndividualUserSummaryResponse(user);
    }

    public List<IndividualUserSummaryResponse> changeIndividualUserStatusBulk(UserBulkStatusChangeRequest request) {
        List<String> emailList = request.getEmailList();
        StatusType statusType = request.getStatusType();

        emailList.forEach(email -> {
            changeIndividualUserStatus(email, statusType);
        });

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Individual User status changed in bulk. Status: " + statusType));

        return IndividualUserConverter.toIndividualUserSummaryResponse(individualUserRepository
                .findByEmailList(emailList));
    }

    public IndividualUserDetailsResponse updateUser(IndividualUserSaveRequest request) {

        IndividualUser userToUpdate = individualUserBusinessRules.checkUserExistByEmail(request.getEmail());

        individualUserBusinessRules.checkEmailAlreadyExist(request.getEmail());
        // TODO: Other validations

        IndividualUser individualUserToUpdate = IndividualUserConverter
                .toUpdatedIndividualUser(userToUpdate, request);
        individualUserRepository.save(individualUserToUpdate);

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Individual User updated. User id: " + individualUserToUpdate.getId()));

        return IndividualUserConverter.toIndividualUserDetailsResponse(individualUserToUpdate);
    }

    public void changePassword(UserPasswordChangeRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        individualUserBusinessRules.checkPasswordValid(password);

        IndividualUser user = individualUserBusinessRules.checkUserExistByEmail(email);

        user.setPassword(password);
        individualUserRepository.save(user);

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Individual user changed password. User id: " + user.getId()));
    }

    public void addRoleToUser(UserRoleRequest request) {
        IndividualUser user = individualUserBusinessRules.checkUserExistByEmail(request.getEmail());
        Role role = roleBusinessRules.checkRoleExistByName(request.getRoleName());

        roleBusinessRules.checkRoleToAddOrRemoveIsDefault(role.getName());

        user.getRoles().add(role);

        individualUserRepository.save(user);

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Role added to individual user. User id: " +
                user.getId() + " Role name: " + role.getName()));
    }

    public void removeRoleFromUser(UserRoleRequest request) {
        IndividualUser user = individualUserBusinessRules.checkUserExistByEmail(request.getEmail());
        Role role = roleBusinessRules.checkRoleExistByName(request.getRoleName());

        roleBusinessRules.checkRoleToAddOrRemoveIsDefault(role.getName());

        user.getRoles().remove(role);

        individualUserRepository.save(user);

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Role removed from individual user. User id: " +
                user.getId() + " Role name: " + role.getName()));
    }

    public List<String> getUserRoles(String email) {
        IndividualUser user = individualUserBusinessRules.checkUserExistByEmail(email);

        return user.getRoles().stream().map(Role::getName).toList();
    }

}
