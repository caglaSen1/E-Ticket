package com.ftbootcamp.notificationservice.service;

import com.ftbootcamp.notificationservice.converter.EmailConverter;
import com.ftbootcamp.notificationservice.dto.request.EmailSendRequest;
import com.ftbootcamp.notificationservice.dto.request.EmailSendWithTemplateRequest;
import com.ftbootcamp.notificationservice.dto.response.EmailResponse;
import com.ftbootcamp.notificationservice.entity.Email;
import com.ftbootcamp.notificationservice.entity.EmailTemplate;
import com.ftbootcamp.notificationservice.producer.kafka.KafkaProducer;
import com.ftbootcamp.notificationservice.producer.kafka.Log;
import com.ftbootcamp.notificationservice.repository.EmailRepository;
import com.ftbootcamp.notificationservice.rules.EmailBusinessRules;
import com.ftbootcamp.notificationservice.rules.EmailTemplateBusinessRules;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final EmailRepository emailRepository;
    private final EmailBusinessRules emailBusinessRules;
    private final EmailTemplateBusinessRules emailTemplateBusinessRules;
    private final KafkaProducer kafkaProducer;

    public EmailResponse sendEmail(EmailSendRequest request){
        Email email = new Email(request.getTo(), request.getText());
        emailRepository.save(email);

        kafkaProducer.sendLogMessage(new Log("Email sent. request: " + request));

        return EmailConverter.toResponse(email);
    }

    public EmailResponse sendEmailWithTemplate(EmailSendWithTemplateRequest request){
        EmailTemplate template = emailTemplateBusinessRules
                .checkTemplateExistByName(request.getEmailTemplateName());

        Email email = new Email(request.getTo(), template.getText());
        emailRepository.save(email);

        kafkaProducer.sendLogMessage(new Log("Email sent with template. request: " + request));

        return EmailConverter.toResponse(email);
    }

    public List<EmailResponse> getAll(){
        return emailRepository.findAll()
                .stream()
                .map(EmailConverter::toResponse)
                .toList();
    }

    public EmailResponse getById(String id){
        return EmailConverter.toResponse(emailBusinessRules.checkEmailExistById(id));
    }
}
