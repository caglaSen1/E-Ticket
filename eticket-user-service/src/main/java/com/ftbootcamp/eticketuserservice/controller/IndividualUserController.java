package com.ftbootcamp.eticketuserservice.controller;

import com.ftbootcamp.eticketuserservice.dto.request.*;
import com.ftbootcamp.eticketuserservice.dto.request.user.UserBulkStatusChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.user.UserPasswordChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.user.UserRoleRequest;
import com.ftbootcamp.eticketuserservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketuserservice.dto.response.individual.IndividualUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.individual.IndividualUserPaginatedResponse;
import com.ftbootcamp.eticketuserservice.dto.response.individual.IndividualUserSummaryResponse;
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
@RequestMapping("/api/v1/users/individual-users")
@RequiredArgsConstructor
@Tag(name = "Individual User API V1", description = "Individual User API for user operations")
public class IndividualUserController {

    private final IndividualUserService individualUserService;

    @GetMapping("/admin-panel/all")
    @Operation(summary = "Get all individual users")
    public GenericResponse<IndividualUserPaginatedResponse> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        return GenericResponse.success(individualUserService.getAllIndividualUsers(page, size), HttpStatus.OK);
    }

    @GetMapping("/profile")
    @Operation(summary = "Get individual user by id", description = "Only active individual user can get his/her own " +
            "information")
    public GenericResponse<IndividualUserDetailsResponse> getUserById(@RequestHeader("Authorization") String token) {
        return GenericResponse.success(individualUserService.getIndividualUserByToken(token), HttpStatus.OK);
    }

    @GetMapping("/admin-panel/{email}")
    @Operation(summary = "Get individual user by email")
    public GenericResponse<IndividualUserDetailsResponse> getUserByEmail(@PathVariable String email) {
        return GenericResponse.success(individualUserService.getIndividualUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/admin-panel/by-status")
    @Operation(summary = "Get individual users by status list")
    public GenericResponse<IndividualUserPaginatedResponse> getUsersByStatusList(
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(required = false) List<StatusType> statusList) {
        return GenericResponse.success(individualUserService.getIndividualUsersByStatusList(page, size, statusList),
                HttpStatus.OK);
    }

    @GetMapping("/admin-panel/by-type")
    @Operation(summary = "Get individual users by type list")
    public GenericResponse<IndividualUserPaginatedResponse> getUsersByTypeList(
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(required = false) List<UserType> typeList) {
        return GenericResponse.success(individualUserService.getIndividualUsersByTypeList(page, size, typeList),
                HttpStatus.OK);
    }

    @GetMapping("/admin-panel/count")
    @Operation(summary = "Get how many individual users")
    public GenericResponse<Integer> getHowManyUsers() {
        return GenericResponse.success(individualUserService.getHowManyIndividualUsers(), HttpStatus.OK);
    }

    @PutMapping("/admin-panel/{email}/{status}")
    @Operation(summary = "Change individual user status")
    public GenericResponse<IndividualUserSummaryResponse> changeStatus(@PathVariable String email,
                                                             @PathVariable("status") StatusType status) {
        return GenericResponse.success(individualUserService.changeIndividualUserStatus(email, status), HttpStatus.OK);
    }

    @PutMapping("/admin-panel/status-bulk")
    @Operation(summary = "Change individual user status bulk")
    public GenericResponse<IndividualUserPaginatedResponse> changeStatusBulk(
                                                            @RequestBody UserBulkStatusChangeRequest request) {
        return GenericResponse.success(individualUserService.changeIndividualUserStatusBulk(request),
                HttpStatus.OK);
    }

    @PostMapping("/update/profile")
    @Operation(summary = "Update individual user", description = "Only active individual user can update his/her own " +
            "information")
    public GenericResponse<IndividualUserDetailsResponse> updateUser(@RequestBody IndividualUserUpdateRequest request,
                                                                     @RequestHeader("Authorization") String token) {
        return GenericResponse.success(individualUserService.updateUser(request, token), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    @Operation(summary = "Change individual user password", description = "Only active individual user can change " +
            "his/her own password")
    public GenericResponse<Void> changePassword(@RequestBody UserPasswordChangeRequest request,
                                                @RequestHeader("Authorization") String token) {
        individualUserService.changePassword(request, token);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PutMapping("/admin-panel/add-role")
    @Operation(summary = "Add role to individual user", description = "Add not default role to user with given email")
    public GenericResponse<Void> addRole(@RequestBody UserRoleRequest request) {
        individualUserService.addRoleToUser(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PutMapping("/admin-panel/remove-role")
    @Operation(summary = "Remove role from individual user", description = "Remove not default role from user with given " +
            "email")
    public GenericResponse<Void> removeRole(@RequestBody UserRoleRequest request) {
        individualUserService.removeRoleFromUser(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @GetMapping("/admin-panel/roles/{email}")
    @Operation(summary = "Get individual user roles", description = "Get individual user roles with given email")
    public GenericResponse<List<String>> getUserRoles(@PathVariable String email) {
        return GenericResponse.success(individualUserService.getUserRoles(email), HttpStatus.OK);
    }

}
