package com.ftbootcamp.eticketauthservice.service;

import com.ftbootcamp.eticketauthservice.entity.concrete.Role;
import com.ftbootcamp.eticketauthservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.eticketauthservice.producer.kafka.Log;
import com.ftbootcamp.eticketauthservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final KafkaProducer kafkaProducer;

    public Role createRoleIfNotExist(String name) {
        if(roleRepository.findByName(name).isEmpty()) {
            roleRepository.save(new Role(name));
        }

        //kafkaProducer.sendLogMessage(new Log("Role added. Role name: " + name));

        return roleRepository.findByName(name).get();
    }
}
