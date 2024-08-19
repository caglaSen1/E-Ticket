package com.ftbootcamp.emailservice.service;

import com.ftbootcamp.emailservice.converter.EmailTemplateConverter;
import com.ftbootcamp.emailservice.dto.request.EmailTemplateCreateRequest;
import com.ftbootcamp.emailservice.dto.request.EmailTemplateUpdateRequest;
import com.ftbootcamp.emailservice.dto.response.EmailTemplateResponse;
import com.ftbootcamp.emailservice.entity.EmailTemplate;
import com.ftbootcamp.emailservice.repository.EmailTemplateRepository;
import com.ftbootcamp.emailservice.rules.EmailTemplateBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailTemplateService {

    private final EmailTemplateRepository emailTemplateRepository;
    private final EmailTemplateBusinessRules emailTemplateBusinessRules;

    public EmailTemplateResponse create(EmailTemplateCreateRequest request) {
        emailTemplateBusinessRules.checkNameAlreadyExist(request.getName());

        log.info("Email template created. template name: {}, template text: {}",
                request.getName(),
                request.getText());

        EmailTemplate emailTemplate = new EmailTemplate(request.getName(), request.getText());
        emailTemplateRepository.save(emailTemplate);

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

        emailTemplateBusinessRules.checkNameAlreadyExist(request.getName());

        EmailTemplate updatedTemplate = EmailTemplateConverter.toUpdatedTemplateEntity(emailTemplateToUpdate, request);

        return EmailTemplateConverter.toResponse(emailTemplateRepository.save(updatedTemplate));
    }

    public EmailTemplateResponse deleteById(String id){
        EmailTemplate emailTemplate = emailTemplateBusinessRules.checkTemplateExistById(id);
        emailTemplateRepository.deleteById(id);

        return EmailTemplateConverter.toResponse(emailTemplate);
    }

    public EmailTemplateResponse deleteByName(String name){
        EmailTemplate emailTemplate = emailTemplateBusinessRules.checkTemplateExistByName(name);
        emailTemplateRepository.delete(emailTemplate);

        return EmailTemplateConverter.toResponse(emailTemplate);
    }
}
