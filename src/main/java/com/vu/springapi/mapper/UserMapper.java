package com.vu.springapi.mapper;

import com.vu.springapi.dto.request.UserCreateRequest;
import com.vu.springapi.dto.request.UserUpdateRequest;
import com.vu.springapi.dto.response.UserResponse;
import com.vu.springapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
    UserResponse toUserResponse(User user);
}
