package com.ftbootcamp.eticketauthservice.service;

import com.ftbootcamp.eticketauthservice.dto.request.AdminUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.CompanyUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.IndividualUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.UserLoginRequest;
import com.ftbootcamp.eticketauthservice.entity.concrete.AdminUser;
import com.ftbootcamp.eticketauthservice.entity.concrete.CompanyUser;
import com.ftbootcamp.eticketauthservice.entity.concrete.IndividualUser;
import com.ftbootcamp.eticketauthservice.entity.concrete.Role;
import com.ftbootcamp.eticketauthservice.entity.constant.RoleEntityConstants;
import com.ftbootcamp.eticketauthservice.model.CustomUser;
import com.ftbootcamp.eticketauthservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.eticketauthservice.producer.rabbitmq.RabbitMqProducer;
import com.ftbootcamp.eticketauthservice.producer.rabbitmq.enums.NotificationType;
import com.ftbootcamp.eticketauthservice.repository.AdminUserRepository;
import com.ftbootcamp.eticketauthservice.repository.CompanyUserRepository;
import com.ftbootcamp.eticketauthservice.repository.IndividualUserRepository;
import com.ftbootcamp.eticketauthservice.rules.RegistrationRules;
import com.ftbootcamp.eticketauthservice.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    //private final UserClientService userClientService;
    private final RoleService roleService;
    private final AdminUserRepository adminUserRepository;
    private final CompanyUserRepository companyUserRepository;
    private final IndividualUserRepository individualUserRepository;
    private final RegistrationRules registrationRules;
    private final RabbitMqProducer rabbitMqProducer;
    private final KafkaProducer kafkaProducer;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String login(UserLoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        CustomUser user = (CustomUser) authentication.getPrincipal();

        return jwtUtil.generateToken(user);
    }

    public void registerAdmin(AdminUserSaveRequest request) {
        registrationRules.checkEmailValid(request.getEmail());
        registrationRules.checkEmailAlreadyExist(request.getEmail());
        registrationRules.checkPasswordValid(request.getPassword());

        // Hash password
        request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        // Create User
        AdminUser createdUser = new AdminUser(request.getEmail(), request.getPhoneNumber(), request.getPassword(),
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
        //rabbitMqProducer.sendMessage(new NotificationSendRequest(notificationTypes, createdUser.getEmail(),
            //    createdUser.getPhoneNumber(), infoMessage));

        // Send log message with Kafka for saving in MongoDB (Asencronize):
       // kafkaProducer.sendLogMessage(new Log("Admin user created. User id: " + createdUser.getId()));

       // return AdminUserConverter.toAdminUserDetailsResponse(createdUser);
    }

    public void registerCompany(CompanyUserSaveRequest request) {
        registrationRules.checkEmailValid(request.getEmail());
        registrationRules.checkEmailAlreadyExist(request.getEmail());
        registrationRules.checkPasswordValid(request.getPassword());

        // Hash password
        request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        // Create User
        CompanyUser createdUser = new CompanyUser(request.getEmail(), request.getPhoneNumber(), request.getPassword(),
                request.getCompanyName(), request.getTaxNumber());

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
        //rabbitMqProducer.sendMessage(new NotificationSendRequest(notificationTypes, createdUser.getEmail(),
                //createdUser.getPhoneNumber(), infoMessage));

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        //kafkaProducer.sendLogMessage(new Log("Company User created. Company User id: " + createdUser.getId()));

        //return CompanyUserConverter.toCompanyUserDetailsResponse(createdUser);
    }

    public void registerIndividual(IndividualUserSaveRequest request) {
        registrationRules.checkEmailValid(request.getEmail());
        registrationRules.checkEmailAlreadyExist(request.getEmail());
        registrationRules.checkPasswordValid(request.getPassword());

        // Hash password
        request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

        // Create User
        IndividualUser createdUser = new IndividualUser(request.getEmail(), request.getPhoneNumber(), request.getPassword(),
                request.getFirstName(), request.getLastName(), request.getNationalId(), request.getBirthDate(),
                request.getGender());

        // Add default role to individual user (USER, INDIVIDUAL_USER)
        Role defaultRole = roleService.createRoleIfNotExist(RoleEntityConstants.USER_ROLE_NAME);
        Role individualUserRole = roleService.createRoleIfNotExist(RoleEntityConstants.INDIVIDUAL_USER_ROLE_NAME);
        createdUser.getRoles().add(defaultRole);
        createdUser.getRoles().add(individualUserRole);

        individualUserRepository.save(createdUser);

        // Send message to user with RabbitMQ Service (Asencronize):
        String infoMessage = "Welcome to our system. Your account created successfully.";
        List<NotificationType> notificationTypes = new ArrayList<>();
        notificationTypes.add(NotificationType.EMAIL);
        if (createdUser.getPhoneNumber() != null) {
            notificationTypes.add(NotificationType.SMS);
        }
        //rabbitMqProducer.sendMessage(new NotificationSendRequest(notificationTypes, createdUser.getEmail(),
            //    createdUser.getPhoneNumber(), infoMessage));

        // Send log message with Kafka for saving in MongoDB (Asencronize):
        //kafkaProducer.sendLogMessage(new Log("Individual User created. User id: " + createdUser.getId()));
    }
}
