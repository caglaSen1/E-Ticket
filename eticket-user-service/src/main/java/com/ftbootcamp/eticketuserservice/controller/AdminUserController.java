package com.ftbootcamp.eticketuserservice.controller;

import com.ftbootcamp.eticketuserservice.dto.request.AdminUserUpdateRequest;
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
@RequestMapping("/api/v1/users/admin-users/admin-panel")
@RequiredArgsConstructor
@Tag(name = "Admin User API V1", description = "Admin User API for user operations")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping("/all")
    @Operation(summary = "Get all admin users")
    public GenericResponse<AdminUserPaginatedResponse> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        return GenericResponse.success(adminUserService.getAllUsers(page, size), HttpStatus.OK);
    }

    @GetMapping("/profile")
    @Operation(summary = "Get admin user by id", description = "Admin user can get only his/her own information")
    public GenericResponse<AdminUserDetailsResponse> getUserById(@RequestHeader("Authorization") String token) {
        return GenericResponse.success(adminUserService.getUserByToken(token), HttpStatus.OK);
    }

    @GetMapping("/by-email/{email}")
    @Operation(summary = "Get admin user by email")
    public GenericResponse<AdminUserDetailsResponse> getUserByEmail(@PathVariable String email) {
        return GenericResponse.success(adminUserService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/count")
    @Operation(summary = "Get how many admin users")
    public GenericResponse<Integer> getHowManyUsers() {
        return GenericResponse.success(adminUserService.getHowManyUsers(), HttpStatus.OK);
    }

    @PostMapping("/update/profile")
    @Operation(summary = "Update admin user", description = "Admin user can update only his/her own information")
    public GenericResponse<AdminUserDetailsResponse> updateUser(@RequestBody AdminUserUpdateRequest request,
                                                                @RequestHeader("Authorization") String token) {
        return GenericResponse.success(adminUserService.updateUser(request, token), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    @Operation(summary = "Change admin user password", description = "Admin user can change only his/her own password")
    public GenericResponse<Void> changePassword(@RequestBody UserPasswordChangeRequest request,
                                                @RequestHeader("Authorization") String token) {
        adminUserService.changePassword(request, token);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PutMapping("/add-role")
    @Operation(summary = "Add role to admin user", description = "Add not default role to admin user with given email")
    public GenericResponse<Void> addRole(@RequestBody UserRoleRequest request) {
        adminUserService.addRoleToUser(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @PutMapping("/remove-role")
    @Operation(summary = "Remove role from admin user", description = "Remove role from admin user with given email")
    public GenericResponse<Void> removeRole(@RequestBody UserRoleRequest request) {
        adminUserService.removeRoleFromUser(request);
        return GenericResponse.success(null, HttpStatus.OK);
    }

    @GetMapping("/roles/{email}")
    @Operation(summary = "Get admin user roles", description = "Get admin user roles with given email")
    public GenericResponse<List<String>> getUserRoles(@PathVariable String email) {
        return GenericResponse.success(adminUserService.getUserRoles(email), HttpStatus.OK);
    }
}
