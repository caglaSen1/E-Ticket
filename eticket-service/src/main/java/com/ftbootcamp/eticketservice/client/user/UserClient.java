package com.ftbootcamp.eticketservice.client.user;

import com.ftbootcamp.eticketservice.client.user.dto.UserDetailsResponse;
import com.ftbootcamp.eticketservice.dto.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${user-service.name}", url = "${user-service.url}")
public interface UserClient {

    @GetMapping("/{id}")
    GenericResponse<UserDetailsResponse> getUserById(@PathVariable Long id);
}
