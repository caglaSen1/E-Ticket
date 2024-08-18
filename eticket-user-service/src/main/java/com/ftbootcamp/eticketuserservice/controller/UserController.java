package com.ftbootcamp.eticketuserservice.controller;

import com.ftbootcamp.eticketuserservice.dto.request.UserBulkStatusChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.UserCreateRequest;
import com.ftbootcamp.eticketuserservice.dto.request.UserPasswordChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.UserRoleRequest;
import com.ftbootcamp.eticketuserservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketuserservice.dto.response.UserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.UserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import com.ftbootcamp.eticketuserservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User API V1", description = "User API for user operations")
public class UserController {

    private final UserService userService;

    @PostMapping()
    @Operation(summary = "Create user", description = "Create user with given user information")
    public GenericResponse<UserDetailsResponse> createUser(@RequestBody UserCreateRequest request) {
        return GenericResponse.success(userService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping()
    @Operation(summary = "Get all users", description = "Get all users")
    public GenericResponse<List<UserSummaryResponse>> getAllUsers() {
        return GenericResponse.success(userService.getAllUsers(), HttpStatus.OK);
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

    @GetMapping("/by-status")
    @Operation(summary = "Get users by status list", description = "Get users by status list")
    public GenericResponse<List<UserSummaryResponse>> getUsersByStatusList(
            @RequestParam(required = false) List<StatusType> statusList) {
        return GenericResponse.success(userService.getUsersByStatusList(statusList), HttpStatus.OK);
    }

    @GetMapping("/by-type")
    @Operation(summary = "Get users by type list", description = "Get users by type list")
    public GenericResponse<List<UserSummaryResponse>> getUsersByTypeList(
            @RequestParam(required = false) List<UserType> typeList) {
        return GenericResponse.success(userService.getUsersByTypeList(typeList), HttpStatus.OK);
    }

    @GetMapping("/count")
    @Operation(summary = "Get how many users", description = "Get how many users")
    public GenericResponse<Integer> getHowManyUsers() {
        return GenericResponse.success(userService.getHowManyUsers(), HttpStatus.OK);
    }

    @PutMapping("/{email}/{status}")
    @Operation(summary = "Change status", description = "Change status of user with given email")
    public GenericResponse<UserSummaryResponse> changeStatus(@PathVariable String email,
                                                    @PathVariable("status") StatusType status) {
        return GenericResponse.success(userService.changeStatus(email, status), HttpStatus.OK);
    }

    @PutMapping("/status-bulk")
    @Operation(summary = "Change status bulk", description = "Change status of users with given email list")
    public GenericResponse<List<UserSummaryResponse>> changeStatusBulk(@RequestBody UserBulkStatusChangeRequest request) {
        return GenericResponse.success(userService.changeStatusBulk(request), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    @Operation(summary = "Change password", description = "Change password of user with given email")
    public GenericResponse<Void> changePassword(@RequestBody UserPasswordChangeRequest request) {
        userService.changePassword(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PutMapping("/add-role")
    @Operation(summary = "Add role", description = "Add role to user with given email")
    public GenericResponse<Void> addRole(@RequestBody UserRoleRequest request) {
        userService.addRoleToUser(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PutMapping("/remove-role")
    @Operation(summary = "Remove role", description = "Remove role from user with given email")
    public GenericResponse<Void> removeRole(@RequestBody UserRoleRequest request) {
        userService.removeRoleFromUser(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @GetMapping("/roles/{email}")
    @Operation(summary = "Get user roles", description = "Get roles of user with given email")
    public GenericResponse<List<String>> getUserRoles(@PathVariable String email) {
        return GenericResponse.success(userService.getUserRoles(email), HttpStatus.OK);
    }
}
