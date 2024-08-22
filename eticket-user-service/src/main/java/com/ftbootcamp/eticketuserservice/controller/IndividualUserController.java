package com.ftbootcamp.eticketuserservice.controller;

import com.ftbootcamp.eticketuserservice.dto.request.*;
import com.ftbootcamp.eticketuserservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketuserservice.dto.response.IndividualUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.IndividualUserSummaryResponse;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import com.ftbootcamp.eticketuserservice.service.IndividualUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/individual-users")
@RequiredArgsConstructor
@Tag(name = "Individual User API V1", description = "Individual User API for user operations")
public class IndividualUserController {

    private final IndividualUserService individualUserService;

    @PostMapping()
    @Operation(summary = "Create individual user", description = "Create individual user with given user information")
    public GenericResponse<IndividualUserDetailsResponse> createUser(@RequestBody IndividualUserRequest request) {
        return GenericResponse.success(individualUserService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping()
    @Operation(summary = "Get all individual users", description = "Get all individual users")
    public GenericResponse<List<IndividualUserSummaryResponse>> getAllUsers() {
        return GenericResponse.success(individualUserService.getAllIndividualUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get individual user by id", description = "Get individual user by id")
    public GenericResponse<IndividualUserDetailsResponse> getUserById(@PathVariable Long id) {
        return GenericResponse.success(individualUserService.getIndividualUserById(id), HttpStatus.OK);
    }

    @GetMapping("/by-email/{email}")
    @Operation(summary = "Get individual user by email", description = "Get individual user by email")
    public GenericResponse<IndividualUserDetailsResponse> getUserByEmail(@PathVariable String email) {
        return GenericResponse.success(individualUserService.getIndividualUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/by-status")
    @Operation(summary = "Get individual users by status list", description = "Get individual users by status list")
    public GenericResponse<List<IndividualUserSummaryResponse>> getUsersByStatusList(
            @RequestParam(required = false) List<StatusType> statusList) {
        return GenericResponse.success(individualUserService.getIndividualUsersByStatusList(statusList), HttpStatus.OK);
    }

    @GetMapping("/by-type")
    @Operation(summary = "Get individual users by type list", description = "Get individual users by type list")
    public GenericResponse<List<IndividualUserSummaryResponse>> getUsersByTypeList(
            @RequestParam(required = false) List<UserType> typeList) {
        return GenericResponse.success(individualUserService.getIndividualUsersByTypeList(typeList), HttpStatus.OK);
    }

    @GetMapping("/count")
    @Operation(summary = "Get how many individual users", description = "Get how many individual users")
    public GenericResponse<Integer> getHowManyUsers() {
        return GenericResponse.success(individualUserService.getHowManyIndividualUsers(), HttpStatus.OK);
    }

    @PutMapping("/{email}/{status}")
    @Operation(summary = "Change individual user status", description = "Change status of user with given email")
    public GenericResponse<IndividualUserSummaryResponse> changeStatus(@PathVariable String email,
                                                             @PathVariable("status") StatusType status) {
        return GenericResponse.success(individualUserService.changeIndividualUserStatus(email, status), HttpStatus.OK);
    }

    @PutMapping("/status-bulk")
    @Operation(summary = "Change individual user status bulk", description = "Change status of users with given email list")
    public GenericResponse<List<IndividualUserSummaryResponse>> changeStatusBulk(@RequestBody UserBulkStatusChangeRequest request) {
        return GenericResponse.success(individualUserService.changeIndividualUserStatusBulk(request), HttpStatus.OK);
    }

    @PostMapping("/update")
    @Operation(summary = "Update individual user", description = "Update individual user with given user information")
    public GenericResponse<IndividualUserDetailsResponse> updateUser(@RequestBody IndividualUserRequest request) {
        return GenericResponse.success(individualUserService.updateUser(request), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    @Operation(summary = "Change individual user password", description = "Change password of user with given email")
    public GenericResponse<Void> changePassword(@RequestBody UserPasswordChangeRequest request) {
        individualUserService.changePassword(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PutMapping("/add-role")
    @Operation(summary = "Add role to individual user", description = "Add role to user with given email")
    public GenericResponse<Void> addRole(@RequestBody UserRoleRequest request) {
        individualUserService.addRoleToUser(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PutMapping("/remove-role")
    @Operation(summary = "Remove role from individual user", description = "Remove role from user with given email")
    public GenericResponse<Void> removeRole(@RequestBody UserRoleRequest request) {
        individualUserService.removeRoleFromUser(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @GetMapping("/roles/{email}")
    @Operation(summary = "Get individual user roles", description = "Get individual user roles with given email")
    public GenericResponse<List<String>> getUserRoles(@PathVariable String email) {
        return GenericResponse.success(individualUserService.getUserRoles(email), HttpStatus.OK);
    }

}
