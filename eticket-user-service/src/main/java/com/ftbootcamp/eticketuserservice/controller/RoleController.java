package com.ftbootcamp.eticketuserservice.controller;

import com.ftbootcamp.eticketuserservice.dto.request.RoleSaveRequest;
import com.ftbootcamp.eticketuserservice.dto.request.RoleUpdateRequest;
import com.ftbootcamp.eticketuserservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketuserservice.dto.response.role.RoleResponse;
import com.ftbootcamp.eticketuserservice.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles/admin-panel")
@RequiredArgsConstructor
@Tag(name = "Role API V1", description = "Role API for role operations")
public class RoleController {

    private final RoleService roleService;

    @PostMapping()
    @Operation(summary = "Add role")
    public GenericResponse<RoleResponse> addRole(@Valid @RequestBody RoleSaveRequest request) {
        return GenericResponse.success(roleService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get role by id")
    public GenericResponse<RoleResponse> getRoleById(@PathVariable Long id) {
        return GenericResponse.success(roleService.getRoleById(id), HttpStatus.OK);
    }

    @GetMapping("/by-name/{name}")
    @Operation(summary = "Get role by name")
    public GenericResponse<RoleResponse> getRoleByName(@PathVariable String name) {
        return GenericResponse.success(roleService.getRoleByName(name), HttpStatus.OK);
    }

    @PostMapping("/update")
    @Operation(summary = "Update role")
    public GenericResponse<RoleResponse> updateRole(@Valid @RequestBody RoleUpdateRequest request) {
        return GenericResponse.success(roleService.updateRole(request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete role")
    public GenericResponse<String> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return GenericResponse.success(null, HttpStatus.OK);
    }

}
