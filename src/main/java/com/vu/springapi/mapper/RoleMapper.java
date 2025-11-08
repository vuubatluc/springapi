package com.vu.springapi.mapper;

import com.vu.springapi.dto.request.RoleRequest;
import com.vu.springapi.dto.response.RoleResponse;
import com.vu.springapi.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
