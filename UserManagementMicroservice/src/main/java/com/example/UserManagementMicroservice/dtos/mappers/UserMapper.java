package com.example.UserManagementMicroservice.dtos.mappers;

import com.example.UserManagementMicroservice.dtos.UpdateUserDto;
import com.example.UserManagementMicroservice.dtos.UserDto;
import com.example.UserManagementMicroservice.models.Role;
import com.example.UserManagementMicroservice.models.User;

public class UserMapper {
    public static User mapToEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        return User.builder()
                .userId(dto.getUserId())
                .name(dto.getName())
                .email(dto.getEmail())
                .role(Role.valueOf(dto.getRole()))
                .build();
    }

    public static UserDto mapToDto(User entity) {
        return new UserDto(entity.getUserId(), entity.getName(), entity.getEmail(), entity.getRole().name());
    }

    public static UpdateUserDto mapToUpdateDto(User entity) {
        return new UpdateUserDto(entity.getName(), entity.getEmail());
    }
}
