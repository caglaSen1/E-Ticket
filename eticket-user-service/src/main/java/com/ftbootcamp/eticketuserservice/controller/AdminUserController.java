package com.ftbootcamp.eticketuserservice.controller;

import com.ftbootcamp.eticketuserservice.dto.request.AdminUserSaveRequest;
import com.ftbootcamp.eticketuserservice.dto.request.user.UserPasswordChangeRequest;
import com.ftbootcamp.eticketuserservice.dto.request.user.UserRoleRequest;
import com.ftbootcamp.eticketuserservice.dto.response.admin.AdminUserDetailsResponse;
import com.ftbootcamp.eticketuserservice.dto.response.admin.AdminUserPaginatedResponse;
import com.ftbootcamp.eticketuserservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketuserservice.service.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/admin-users")
@RequiredArgsConstructor
@Tag(name = "Admin User API V1", description = "Admin User API for user operations")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @PostMapping()
    @Operation(summary = "Create admin user", description = "Create admin user with given user information")
    public GenericResponse<AdminUserDetailsResponse> createUser(@RequestBody AdminUserSaveRequest request) {
        return GenericResponse.success(adminUserService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping()
    @Operation(summary = "Get all admin users", description = "Get all admin users, only for admin users")
    public GenericResponse<AdminUserPaginatedResponse> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        return GenericResponse.success(adminUserService.getAllUsers(page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get admin user by id", description = "Get admin user by id, only for admin users")
    public GenericResponse<AdminUserDetailsResponse> getUserById(@PathVariable Long id) {
        return GenericResponse.success(adminUserService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/by-email/{email}")
    @Operation(summary = "Get admin user by email", description = "Get admin user by email, only for admin users")
    public GenericResponse<AdminUserDetailsResponse> getUserByEmail(@PathVariable String email) {
        return GenericResponse.success(adminUserService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/count")
    @Operation(summary = "Get how many admin users", description = "Get how many admin users, only for admin users")
    public GenericResponse<Integer> getHowManyUsers() {
        return GenericResponse.success(adminUserService.getHowManyUsers(), HttpStatus.OK);
    }

    @PostMapping("/update")
    @Operation(summary = "Update admin user", description = "Update admin user with given user information, " +
            "only for admin users")
    public GenericResponse<AdminUserDetailsResponse> updateUser(@RequestBody AdminUserSaveRequest request) {
        return GenericResponse.success(adminUserService.updateUser(request), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    @Operation(summary = "Change admin user password", description = "Change password of admin user with given email, " +
            "only for admin users")
    public GenericResponse<Void> changePassword(@RequestBody UserPasswordChangeRequest request) {
        adminUserService.changePassword(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PutMapping("/add-role")
    @Operation(summary = "Add role to admin user", description = "Add not default role to admin user with given email, "
            + "only for admin users")
    public GenericResponse<Void> addRole(@RequestBody UserRoleRequest request) {
        adminUserService.addRoleToUser(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PutMapping("/remove-role")
    @Operation(summary = "Remove role from admin user", description = "Remove role from admin user with given email, "
            + "only for admin users")
    public GenericResponse<Void> removeRole(@RequestBody UserRoleRequest request) {
        adminUserService.removeRoleFromUser(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @GetMapping("/roles/{email}")
    @Operation(summary = "Get admin user roles", description = "Get admin user roles with given email, " +
            "only for admin users")
    public GenericResponse<List<String>> getUserRoles(@PathVariable String email) {
        return GenericResponse.success(adminUserService.getUserRoles(email), HttpStatus.OK);
    }
}
