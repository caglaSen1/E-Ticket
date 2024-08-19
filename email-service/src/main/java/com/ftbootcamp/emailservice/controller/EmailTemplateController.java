package com.ftbootcamp.emailservice.controller;

import com.ftbootcamp.emailservice.dto.request.EmailTemplateCreateRequest;
import com.ftbootcamp.emailservice.dto.request.EmailTemplateUpdateRequest;
import com.ftbootcamp.emailservice.dto.response.EmailTemplateResponse;
import com.ftbootcamp.emailservice.dto.response.GenericResponse;
import com.ftbootcamp.emailservice.service.EmailTemplateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/email-templates")
@RequiredArgsConstructor
@Tag(name = "Email Template API V1", description = "Email Template API for email template operations")
public class EmailTemplateController {

    private final EmailTemplateService emailTemplateService;

    @PostMapping()
    public GenericResponse<EmailTemplateResponse> addEmailTemplate(@RequestBody EmailTemplateCreateRequest request) {
        return GenericResponse.success(emailTemplateService.create(request), HttpStatus.CREATED);
    }

    @GetMapping()
    public GenericResponse<List<EmailTemplateResponse>> getAllEmailTemplates() {
        return GenericResponse.success(emailTemplateService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public GenericResponse<EmailTemplateResponse> getEmailTemplateById(@PathVariable String id) {
        return GenericResponse.success(emailTemplateService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/by-name/{name}")
    public GenericResponse<EmailTemplateResponse> getEmailTemplateByName(@PathVariable String name) {
        return GenericResponse.success(emailTemplateService.getByName(name), HttpStatus.OK);
    }

    @PostMapping("/update")
    public GenericResponse<EmailTemplateResponse> updateEmailTemplate(@RequestBody EmailTemplateUpdateRequest request) {
        return GenericResponse.success(emailTemplateService.update(request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public GenericResponse<EmailTemplateResponse> deleteEmailTemplate(@PathVariable String id) {
        return GenericResponse.success(emailTemplateService.deleteById(id), HttpStatus.OK);
    }

    @DeleteMapping("/by-name/{name}")
    public GenericResponse<EmailTemplateResponse> deleteEmailTemplateByName(@PathVariable String name) {
        return GenericResponse.success(emailTemplateService.deleteByName(name), HttpStatus.OK);
    }

}
