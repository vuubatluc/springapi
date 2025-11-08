package com.vu.springapi.service;

import com.vu.springapi.dto.request.PermissionRequest;
import com.vu.springapi.dto.request.RoleRequest;
import com.vu.springapi.dto.response.PermissionResponse;
import com.vu.springapi.dto.response.RoleResponse;
import com.vu.springapi.mapper.PermissionMapper;
import com.vu.springapi.mapper.RoleMapper;
import com.vu.springapi.model.Permission;
import com.vu.springapi.model.Role;
import com.vu.springapi.repository.PermissionRepository;
import com.vu.springapi.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest request){
        Role role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String role){
        roleRepository.deleteById(role);
    }
}
