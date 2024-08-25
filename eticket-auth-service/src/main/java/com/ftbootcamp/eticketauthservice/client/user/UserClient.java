package com.ftbootcamp.eticketauthservice.client.user;

import com.ftbootcamp.eticketauthservice.dto.request.AdminUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.CompanyUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.request.IndividualUserSaveRequest;
import com.ftbootcamp.eticketauthservice.dto.response.AdminUserDetailsResponse;
import com.ftbootcamp.eticketauthservice.dto.response.CompanyUserDetailsResponse;
import com.ftbootcamp.eticketauthservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketauthservice.dto.response.IndividualUserDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "localhost:9091/api/v1/users")
public interface UserClient {

    @PostMapping("/admin-users")
    GenericResponse<AdminUserDetailsResponse> createUser(@RequestBody AdminUserSaveRequest request);

    @PostMapping("/company-users")
    GenericResponse<CompanyUserDetailsResponse> createUser(@RequestBody CompanyUserSaveRequest request);

    @PostMapping("/individual-users")
    GenericResponse<IndividualUserDetailsResponse> createUser(@RequestBody IndividualUserSaveRequest request);
}
