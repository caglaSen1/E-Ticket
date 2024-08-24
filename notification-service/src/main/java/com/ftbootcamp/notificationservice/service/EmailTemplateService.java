package com.ftbootcamp.notificationservice.service;

import com.ftbootcamp.notificationservice.converter.EmailTemplateConverter;
import com.ftbootcamp.notificationservice.dto.request.EmailTemplateCreateRequest;
import com.ftbootcamp.notificationservice.dto.request.EmailTemplateUpdateRequest;
import com.ftbootcamp.notificationservice.dto.response.EmailTemplateResponse;
import com.ftbootcamp.notificationservice.entity.EmailTemplate;
import com.ftbootcamp.notificationservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.notificationservice.producer.kafka.Log;
import com.ftbootcamp.notificationservice.repository.EmailTemplateRepository;
import com.ftbootcamp.notificationservice.rules.EmailTemplateBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailTemplateService {

    private final EmailTemplateRepository emailTemplateRepository;
    private final EmailTemplateBusinessRules emailTemplateBusinessRules;
    private final KafkaProducer kafkaProducer;

    public EmailTemplateResponse create(EmailTemplateCreateRequest request) {
        emailTemplateBusinessRules.checkNameAlreadyExist(request.getName());

        log.info("Email template created. template name: {}, template text: {}",
                request.getName(),
                request.getText());

        EmailTemplate emailTemplate = new EmailTemplate(request.getName(), request.getText());
        emailTemplateRepository.save(emailTemplate);

        kafkaProducer.sendLogMessage(new Log("Email template created. request: " + request));

        return EmailTemplateConverter.toResponse(emailTemplate);
    }

    public List<EmailTemplateResponse> getAll(){
        return emailTemplateRepository.findAll()
                .stream()
                .map(EmailTemplateConverter::toResponse)
                .collect(Collectors.toList());
    }

    public EmailTemplateResponse getById(String id) {
        EmailTemplate emailTemplate = emailTemplateBusinessRules.checkTemplateExistById(id);
        return EmailTemplateConverter.toResponse(emailTemplate);
    }

    public EmailTemplateResponse getByName(String name) {
        EmailTemplate emailTemplate = emailTemplateBusinessRules.checkTemplateExistByName(name);
        return EmailTemplateConverter.toResponse(emailTemplate);
    }

    public EmailTemplateResponse update(EmailTemplateUpdateRequest request){
        EmailTemplate emailTemplateToUpdate = emailTemplateBusinessRules.checkTemplateExistById(request.getId());

        if(!request.getName().toUpperCase().equals(emailTemplateToUpdate.getName())){
            emailTemplateBusinessRules.checkNameAlreadyExist(request.getName());
        }

        EmailTemplate updatedTemplate = EmailTemplateConverter.toUpdatedTemplateEntity(emailTemplateToUpdate, request);

        kafkaProducer.sendLogMessage(new Log("Email template updated. request: " + request));

        return EmailTemplateConverter.toResponse(emailTemplateRepository.save(updatedTemplate));
    }

    public EmailTemplateResponse deleteById(String id){
        EmailTemplate emailTemplate = emailTemplateBusinessRules.checkTemplateExistById(id);
        emailTemplateRepository.deleteById(id);  // TODO: soft delete

        kafkaProducer.sendLogMessage(new Log("Email template deleted. Id: " + id));

        return EmailTemplateConverter.toResponse(emailTemplate);
    }

    public EmailTemplateResponse deleteByName(String name){
        EmailTemplate emailTemplate = emailTemplateBusinessRules.checkTemplateExistByName(name);
        emailTemplateRepository.delete(emailTemplate);  // TODO: soft delete

        kafkaProducer.sendLogMessage(new Log("Email template deleted. Name: " + name));

        return EmailTemplateConverter.toResponse(emailTemplate);
    }
}
