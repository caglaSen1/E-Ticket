package com.ftbootcamp.eticketuserservice.controller;

import com.ftbootcamp.eticketuserservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketuserservice.dto.response.UserSummaryPaginatedResponse;
import com.ftbootcamp.eticketuserservice.dto.response.UserDetailsResponse;
import com.ftbootcamp.eticketuserservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User API V1", description = "User API for user operations")
public class UserController {

    private final UserService userService;

    @GetMapping()
    @Operation(summary = "Get all users", description = "Get all users")
    public GenericResponse<UserSummaryPaginatedResponse> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        return GenericResponse.success(userService.getAllUsers(page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id", description = "Get user by id")
    public GenericResponse<UserDetailsResponse> getUserById(@PathVariable Long id) {
        return GenericResponse.success(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/by-email/{email}")
    @Operation(summary = "Get user by email", description = "Get user by email")
    public GenericResponse<UserDetailsResponse> getUserByEmail(@PathVariable String email) {
        return GenericResponse.success(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/count")
    @Operation(summary = "Get how many users", description = "Get how many users")
    public GenericResponse<Integer> getHowManyUsers() {
        return GenericResponse.success(userService.getHowManyUsers(), HttpStatus.OK);
    }
}
