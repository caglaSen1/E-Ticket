package com.ftbootcamp.emailservice.converter;

import com.ftbootcamp.emailservice.dto.response.EmailResponse;
import com.ftbootcamp.emailservice.entity.Email;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class EmailConverter {

    public static EmailResponse toResponse(Email email) {

        return EmailResponse.builder()
                .to(email.getTo())
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
