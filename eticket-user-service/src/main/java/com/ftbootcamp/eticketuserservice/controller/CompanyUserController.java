package com.ftbootcamp.eticketuserservice.controller;

import com.ftbootcamp.eticketuserservice.dto.request.*;
import com.ftbootcamp.eticketuserservice.dto.response.CompanyUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.CompanyUserSummaryResponse;
import com.ftbootcamp.eticketuserservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketuserservice.entity.enums.StatusType;
import com.ftbootcamp.eticketuserservice.entity.enums.UserType;
import com.ftbootcamp.eticketuserservice.service.CompanyUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/company-users")
@RequiredArgsConstructor
@Tag(name = "Company User API V1", description = "Company User API for user operations")
public class CompanyUserController {

    private final CompanyUserService companyUserService;

    @PostMapping()
    @Operation(summary = "Create company user", description = "Create company user with given user information")
    public GenericResponse<CompanyUserDetailsResponse> createUser(@RequestBody CompanyUserRequest request) {
        return GenericResponse.success(companyUserService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping()
    @Operation(summary = "Get all company users", description = "Get all company users")
    public GenericResponse<List<CompanyUserSummaryResponse>> getAllUsers() {
        return GenericResponse.success(companyUserService.getAllCompanyUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get company user by id", description = "Get company user by id")
    public GenericResponse<CompanyUserDetailsResponse> getUserById(@PathVariable Long id) {
        return GenericResponse.success(companyUserService.getCompanyUserById(id), HttpStatus.OK);
    }

    @GetMapping("/by-email/{email}")
    @Operation(summary = "Get company user by email", description = "Get company user by email")
    public GenericResponse<CompanyUserDetailsResponse> getUserByEmail(@PathVariable String email) {
        return GenericResponse.success(companyUserService.getCompanyUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/by-status")
    @Operation(summary = "Get company users by status list", description = "Get company users by status list")
    public GenericResponse<List<CompanyUserSummaryResponse>> getUsersByStatusList(
            @RequestParam(required = false) List<StatusType> statusList) {
        return GenericResponse.success(companyUserService.getCompanyUsersByStatusList(statusList), HttpStatus.OK);
    }

    @GetMapping("/by-type")
    @Operation(summary = "Get company users by type list", description = "Get company users by type list")
    public GenericResponse<List<CompanyUserSummaryResponse>> getUsersByTypeList(
            @RequestParam(required = false) List<UserType> typeList) {
        return GenericResponse.success(companyUserService.getCompanyUsersByTypeList(typeList), HttpStatus.OK);
    }

    @GetMapping("/count")
    @Operation(summary = "Get how many company users", description = "Get how many company users")
    public GenericResponse<Integer> getHowManyUsers() {
        return GenericResponse.success(companyUserService.getHowManyCompanyUsers(), HttpStatus.OK);
    }

    @PutMapping("/{email}/{status}")
    @Operation(summary = "Change company user status", description = "Change status of user with given email")
    public GenericResponse<CompanyUserSummaryResponse> changeStatus(@PathVariable String email,
                                                             @PathVariable("status") StatusType status) {
        return GenericResponse.success(companyUserService.changeStatus(email, status), HttpStatus.OK);
    }

    @PutMapping("/status-bulk")
    @Operation(summary = "Change company user status bulk", description = "Change status of users with given email list")
    public GenericResponse<List<CompanyUserSummaryResponse>> changeStatusBulk(@RequestBody UserBulkStatusChangeRequest request) {
        return GenericResponse.success(companyUserService.changeCompanyUserStatusBulk(request), HttpStatus.OK);
    }

    @PostMapping("/update")
    @Operation(summary = "Update company user", description = "Update company user with given user information")
    public GenericResponse<CompanyUserDetailsResponse> updateUser(@RequestBody CompanyUserRequest request) {
        return GenericResponse.success(companyUserService.updateUser(request), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    @Operation(summary = "Change company user password", description = "Change password of user with given email")
    public GenericResponse<Void> changePassword(@RequestBody UserPasswordChangeRequest request) {
        companyUserService.changePassword(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PutMapping("/add-role")
    @Operation(summary = "Add role to company user", description = "Add role to user with given email")
    public GenericResponse<Void> addRole(@RequestBody UserRoleRequest request) {
        companyUserService.addRoleToUser(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PutMapping("/remove-role")
    @Operation(summary = "Remove role from company user", description = "Remove role from user with given email")
    public GenericResponse<Void> removeRole(@RequestBody UserRoleRequest request) {
        companyUserService.removeRoleFromUser(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @GetMapping("/roles/{email}")
    @Operation(summary = "Get company user roles", description = "Get user roles with given email")
    public GenericResponse<List<String>> getUserRoles(@PathVariable String email) {
        return GenericResponse.success(companyUserService.getUserRoles(email), HttpStatus.OK);
    }

}
