package com.ftbootcamp.eticketuserservice.service;

import com.ftbootcamp.eticketuserservice.converter.AdminUserConverter;
import com.ftbootcamp.eticketuserservice.dto.request.AdminUserSaveRequest;
import com.ftbootcamp.eticketuserservice.dto.request.user.UserPasswordChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.user.UserRoleRequest;
import com.ftbootcamp.eticketuserservice.dto.response.admin.AdminUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.admin.AdminUserPaginatedResponse;
import com.ftbootcamp.eticketuserservice.entity.concrete.AdminUser;
import com.ftbootcamp.eticketuserservice.entity.concrete.Role;
import com.ftbootcamp.eticketuserservice.entity.constant.RoleEntityConstants;
import com.ftbootcamp.eticketuserservice.producer.kafka.Log;
import com.ftbootcamp.eticketuserservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.eticketuserservice.producer.rabbitmq.RabbitMqProducer;
import com.ftbootcamp.eticketuserservice.producer.rabbitmq.dto.NotificationSendRequest;
import com.ftbootcamp.eticketuserservice.producer.rabbitmq.enums.NotificationType;
import com.ftbootcamp.eticketuserservice.repository.AdminUserRepository;
import com.ftbootcamp.eticketuserservice.rules.AdminUserBusinessRules;
import com.ftbootcamp.eticketuserservice.rules.RoleBusinessRules;
import com.ftbootcamp.eticketuserservice.util.ExtractFromToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ftbootcamp.eticketuserservice.core.PasswordHasher.hashPassword;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final AdminUserBusinessRules adminUserBusinessRules;
    private final RoleBusinessRules roleBusinessRules;
    private final KafkaProducer kafkaProducer;

    public AdminUserPaginatedResponse getAllUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return AdminUserConverter.toAdminUserPaginatedResponse(adminUserRepository.findAll(pageRequest));
    }

    public AdminUserDetailsResponse getUserByToken(String token) {
        String email = ExtractFromToken.email(token);
        return AdminUserConverter.toAdminUserDetailsResponse(adminUserBusinessRules.checkUserExistByEmail(email));
    }

    public AdminUserDetailsResponse getUserByEmail(String email) {
        return AdminUserConverter.toAdminUserDetailsResponse(adminUserBusinessRules.checkUserExistByEmail(email));
    }

    public int getHowManyUsers() {
        return (int) adminUserRepository.count();
    }

    public AdminUserDetailsResponse updateUser(AdminUserSaveRequest request, String token){
        String email = ExtractFromToken.email(token);

        AdminUser userToUpdate = adminUserBusinessRules.checkUserExistByEmail(email);
        adminUserBusinessRules.checkEmailAlreadyExist(request.getEmail());
        adminUserBusinessRules.checkPasswordValid(request.getPassword());

        AdminUser adminUserToUpdate = AdminUserConverter.toUpdatedAdminUser(userToUpdate, request);
        adminUserRepository.save(adminUserToUpdate);

        return AdminUserConverter.toAdminUserDetailsResponse(adminUserToUpdate);
    }

    public void changePassword(UserPasswordChangeRequest request, String token) {
        String email = ExtractFromToken.email(token);
        String password = request.getPassword();

        AdminUser user = adminUserBusinessRules.checkUserExistByEmail(email);
        adminUserBusinessRules.checkPasswordValid(password);

        user.setPassword(password);
        adminUserRepository.save(user);

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Admin user changed password. User id: " + user.getId()));
    }

    public void addRoleToUser(UserRoleRequest request) {
        AdminUser user = adminUserBusinessRules.checkUserExistByEmail(request.getEmail());
        Role role = roleBusinessRules.checkRoleExistByName(request.getRoleName());

        roleBusinessRules.checkRoleToAddOrRemoveIsDefault(role.getName());

        user.getRoles().add(role);

        adminUserRepository.save(user);

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Role added to admin user. User id: " +
                user.getId() + " Role name: " + role.getName()));
    }

    public void removeRoleFromUser(UserRoleRequest request) {
        AdminUser user = adminUserBusinessRules.checkUserExistByEmail(request.getEmail());
        Role role = roleBusinessRules.checkRoleExistByName(request.getRoleName());

        roleBusinessRules.checkRoleToAddOrRemoveIsDefault(role.getName());

        user.getRoles().remove(role);

        adminUserRepository.save(user);

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        kafkaProducer.sendLogMessage(new Log("Role removed from admin user. User id: " +
                user.getId() + " Role name: " + role.getName()));
    }

    public List<String> getUserRoles(String email) {
        AdminUser user = adminUserBusinessRules.checkUserExistByEmail(email);

        return user.getRoles().stream().map(Role::getName).toList();
    }
}
