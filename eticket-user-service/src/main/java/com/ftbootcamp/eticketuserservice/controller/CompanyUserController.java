package com.ftbootcamp.eticketuserservice.controller;

import com.ftbootcamp.eticketuserservice.dto.request.*;
import com.ftbootcamp.eticketuserservice.dto.request.user.UserBulkStatusChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.user.UserPasswordChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.user.UserRoleRequest;
import com.ftbootcamp.eticketuserservice.dto.response.company.CompanyUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.company.CompanyUserPaginatedResponse;
import com.ftbootcamp.eticketuserservice.dto.response.company.CompanyUserSummaryResponse;
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
@RequestMapping("/api/v1/users/company-users")
@RequiredArgsConstructor
@Tag(name = "Company User API V1", description = "Company User API for user operations")
public class CompanyUserController {

    private final CompanyUserService companyUserService;

    @GetMapping("/admin-panel/all")
    @Operation(summary = "Get all company users")
    public GenericResponse<List<CompanyUserSummaryResponse>> getAllUsers() {
        return GenericResponse.success(companyUserService.getAllCompanyUsers(), HttpStatus.OK);
    }

    @GetMapping("/profile")
    @Operation(summary = "Get company user by id", description = "Only active company user can get his/her own information")
    public GenericResponse<CompanyUserDetailsResponse> getUserByToken(@RequestHeader("Authorization") String token) {
        return GenericResponse.success(companyUserService.getCompanyUserByToken(token), HttpStatus.OK);
    }

    @GetMapping("/admin-panel/{email}")
    @Operation(summary = "Get company user by email")
    public GenericResponse<CompanyUserDetailsResponse> getUserByEmail(@PathVariable String email) {
        return GenericResponse.success(companyUserService.getCompanyUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/admin-panel/by-status")
    @Operation(summary = "Get company users by status list")
    public GenericResponse<CompanyUserPaginatedResponse> getUsersByStatusList(
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(required = false) List<StatusType> statusList) {
        return GenericResponse.success(companyUserService.getCompanyUsersByStatusList(page, size, statusList),
                HttpStatus.OK);
    }

    @GetMapping("/admin-panel/by-type")
    @Operation(summary = "Get company users by type list")
    public GenericResponse<CompanyUserPaginatedResponse> getUsersByTypeList(
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(required = false) List<UserType> typeList) {
        return GenericResponse.success(companyUserService.getCompanyUsersByTypeList(page, size, typeList),
                HttpStatus.OK);
    }

    @GetMapping("/admin-panel/count")
    @Operation(summary = "Get how many company users")
    public GenericResponse<Integer> getHowManyUsers() {
        return GenericResponse.success(companyUserService.getHowManyCompanyUsers(), HttpStatus.OK);
    }

    @PutMapping("/admin-panel/{email}/{status}")
    @Operation(summary = "Change company user status")
    public GenericResponse<CompanyUserSummaryResponse> changeStatus(@PathVariable String email,
                                                             @PathVariable("status") StatusType status) {
        return GenericResponse.success(companyUserService.changeStatus(email, status), HttpStatus.OK);
    }

    @PutMapping("/admin-panel/status-bulk")
    @Operation(summary = "Change company user status bulk")
    public GenericResponse<CompanyUserPaginatedResponse> changeStatusBulk(
                                                              @RequestBody UserBulkStatusChangeRequest request) {
        return GenericResponse.success(companyUserService.changeCompanyUserStatusBulk(request),
                HttpStatus.OK);
    }

    @PostMapping("/update/profile")
    @Operation(summary = "Update company user", description = "Only active company user can update his/her own information")
    public GenericResponse<CompanyUserDetailsResponse> updateUser(@RequestBody CompanyUserUpdateRequest request,
                                                                  @RequestHeader("Authorization") String token) {
        return GenericResponse.success(companyUserService.updateUser(request, token), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    @Operation(summary = "Change company user password", description = "Only active company user can change his/her own " +
            "password")
    public GenericResponse<Void> changePassword(@RequestBody UserPasswordChangeRequest request,
                                                @RequestHeader("Authorization") String token) {
        companyUserService.changePassword(request, token);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PutMapping("/admin-panel/add-role")
    @Operation(summary = "Add role to company user", description = "Add not default role to user with given email")
    public GenericResponse<Void> addRole(@RequestBody UserRoleRequest request) {
        companyUserService.addRoleToUser(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PutMapping("/admin-panel/remove-role")
    @Operation(summary = "Remove role from company user", description = "Remove not default role from user with given email")
    public GenericResponse<Void> removeRole(@RequestBody UserRoleRequest request) {
        companyUserService.removeRoleFromUser(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @GetMapping("/admin-panel/roles/{email}")
    @Operation(summary = "Get company user roles", description = "Get user roles with given email")
    public GenericResponse<List<String>> getUserRoles(@PathVariable String email) {
        return GenericResponse.success(companyUserService.getUserRoles(email), HttpStatus.OK);
    }

}
