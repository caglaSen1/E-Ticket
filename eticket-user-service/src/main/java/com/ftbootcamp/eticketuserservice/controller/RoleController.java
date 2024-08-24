package com.ftbootcamp.eticketuserservice.controller;

import com.ftbootcamp.eticketuserservice.dto.request.RoleSaveRequest;
import com.ftbootcamp.eticketuserservice.dto.request.RoleUpdateRequest;
import com.ftbootcamp.eticketuserservice.dto.response.GenericResponse;
import com.ftbootcamp.eticketuserservice.dto.response.RoleResponse;
import com.ftbootcamp.eticketuserservice.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Role API V1", description = "Role API for role operations")
public class RoleController {

    private final RoleService roleService;

    @PostMapping()
    @Operation(summary = "Add role", description = "Add role with given role information, authorized for admin users only")
    public GenericResponse<RoleResponse> addRole(@RequestBody RoleSaveRequest request) {
        return GenericResponse.success(roleService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get role by id", description = "Get role by id, authorized for admin users only")
    public GenericResponse<RoleResponse> getRoleById(@PathVariable Long id) {
        return GenericResponse.success(roleService.getRoleById(id), HttpStatus.OK);
    }

    @GetMapping("/by-name/{name}")
    @Operation(summary = "Get role by name", description = "Get role by name, authorized for admin users only")
    public GenericResponse<RoleResponse> getRoleByName(@PathVariable String name) {
        return GenericResponse.success(roleService.getRoleByName(name), HttpStatus.OK);
    }

    @PostMapping("/update")
    @Operation(summary = "Update role", description = "Update role with given role information, authorized for admin users only")
    public GenericResponse<RoleResponse> updateRole(@RequestBody RoleUpdateRequest request) {
        return GenericResponse.success(roleService.updateRole(request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete role", description = "Delete role by id, authorized for admin users only")
    public GenericResponse<String> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return GenericResponse.success(null, HttpStatus.OK);
    }

}
