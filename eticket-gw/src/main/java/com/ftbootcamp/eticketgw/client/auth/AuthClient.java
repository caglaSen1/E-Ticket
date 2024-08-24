package com.ftbootcamp.eticketgw.client.auth;

import com.ftbootcamp.eticketgw.client.auth.dto.GenericResponse;
import com.ftbootcamp.eticketgw.client.auth.dto.UserDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "eticket-auth-service", url = "localhost:9069/api/v1/auth")
public interface AuthClient {

    @GetMapping("/by-email/{email}")
    GenericResponse<UserDetailsResponse> getUserByEmail(@PathVariable String email);

}
