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
    private final RoleService roleService;
    private final RabbitMqProducer rabbitMqProducer;
    private final KafkaProducer kafkaProducer;

    public AdminUserDetailsResponse createUser(AdminUserSaveRequest request) {
        adminUserBusinessRules.checkEmailValid(request.getEmail());
        adminUserBusinessRules.checkEmailAlreadyExist(request.getEmail());
        adminUserBusinessRules.checkPasswordValid(request.getPassword());

        // Hash password
        String hashedPassword = hashPassword(request.getPassword());

        // Create User
        AdminUser createdUser = new AdminUser(request.getEmail(), request.getPhoneNumber(), hashedPassword,
                request.getFirstName(), request.getLastName(), request.getNationalId(), request.getGender());

        // Add default role to admin user (USER, ADMIN_USER)
        Role userRole = roleService.createRoleIfNotExist(RoleEntityConstants.USER_ROLE_NAME);
        Role adminUserRole = roleService.createRoleIfNotExist(RoleEntityConstants.ADMIN_USER_ROLE_NAME);
        createdUser.getRoles().add(userRole);
        createdUser.getRoles().add(adminUserRole);

        adminUserRepository.save(createdUser);

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
        kafkaProducer.sendLogMessage(new Log("Admin user created. User id: " + createdUser.getId()));

        return AdminUserConverter.toAdminUserDetailsResponse(createdUser);
    }

    public AdminUserPaginatedResponse getAllUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return AdminUserConverter.toAdminUserPaginatedResponse(adminUserRepository.findAll(pageRequest));
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

    public AdminUserDetailsResponse updateUser(AdminUserSaveRequest request){

        AdminUser userToUpdate = adminUserBusinessRules.checkUserExistByEmail(request.getEmail());

        adminUserBusinessRules.checkEmailAlreadyExist(request.getEmail());
        // TODO: Other validations

        AdminUser adminUserToUpdate = AdminUserConverter.toUpdatedAdminUser(userToUpdate, request);
        adminUserRepository.save(adminUserToUpdate);

        return AdminUserConverter.toAdminUserDetailsResponse(adminUserToUpdate);
    }

    public void changePassword(UserPasswordChangeRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        adminUserBusinessRules.checkPasswordValid(password);

        AdminUser user = adminUserBusinessRules.checkUserExistByEmail(email);

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
