package com.ftbootcamp.notificationservice.controller;

import com.ftbootcamp.notificationservice.dto.request.EmailSendRequest;
import com.ftbootcamp.notificationservice.dto.request.EmailSendWithTemplateRequest;
import com.ftbootcamp.notificationservice.dto.response.EmailResponse;
import com.ftbootcamp.notificationservice.dto.response.GenericResponse;
import com.ftbootcamp.notificationservice.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
@Tag(name = "Email API V1", description = "Email API for sending emails")
public class EmailController {

    private final EmailService emailService;

    @PostMapping()
    @Operation(summary = "Send email", description = "Send email with given address and text")
    public GenericResponse<EmailResponse> sendEmail(@RequestBody EmailSendRequest request){
        return GenericResponse.success(emailService.sendEmail(request), HttpStatus.CREATED);
    }

    @PostMapping("/by-template")
    @Operation(summary = "Send email by template", description = "Send email with given template, without text")
    public GenericResponse<EmailResponse> sendEmailWithTemplate(@RequestBody EmailSendWithTemplateRequest request){
        return GenericResponse.success(emailService.sendEmailWithTemplate(request), HttpStatus.CREATED);
    }

    @GetMapping()
    @Operation(summary = "Get all emails")
    public GenericResponse<List<EmailResponse>> getAll(){
        return GenericResponse.success(emailService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get email by id")
    public GenericResponse<EmailResponse> getById(@PathVariable String id){
        return GenericResponse.success(emailService.getById(id), HttpStatus.OK);
    }
}
