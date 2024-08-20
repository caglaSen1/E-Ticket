package com.ftbootcamp.notificationservice.converter;

import com.ftbootcamp.notificationservice.dto.request.EmailTemplateUpdateRequest;
import com.ftbootcamp.notificationservice.dto.response.EmailTemplateResponse;
import com.ftbootcamp.notificationservice.entity.EmailTemplate;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class EmailTemplateConverter {

    public static EmailTemplateResponse toResponse(EmailTemplate emailTemplate) {

        return EmailTemplateResponse.builder()
                .name(emailTemplate.getName())
                .text(emailTemplate.getText())
                .createdDateTime(emailTemplate.getCreatedDateTime())
                .build();
    }

    public static EmailTemplate toUpdatedTemplateEntity(EmailTemplate templateToUpdate,
                                                                EmailTemplateUpdateRequest emailTemplateUpdateRequest){
        if(emailTemplateUpdateRequest.getName() != null){
            templateToUpdate.setName(emailTemplateUpdateRequest.getName().toUpperCase());
        }
        if(emailTemplateUpdateRequest.getText() != null){
            templateToUpdate.setText(emailTemplateUpdateRequest.getText());
        }

        return templateToUpdate;
    }
}
