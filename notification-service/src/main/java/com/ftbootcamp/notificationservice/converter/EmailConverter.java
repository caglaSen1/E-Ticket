package com.ftbootcamp.notificationservice.converter;

import com.ftbootcamp.notificationservice.dto.response.EmailResponse;
import com.ftbootcamp.notificationservice.entity.Email;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class EmailConverter {

    public static EmailResponse toResponse(Email email) {

        return EmailResponse.builder()
                .to(email.getEmail())
                .text(email.getText())
                .createdDateTime(email.getCreatedDateTime())
                .build();
    }

    public static List<EmailResponse> toResponse(List<Email> emails) {

        return emails.stream()
                .map(EmailConverter::toResponse)
                .toList();
    }
}
