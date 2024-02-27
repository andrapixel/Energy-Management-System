package com.example.UserManagementMicroservice.dtos;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID userId;
    private String name;
    private String email;
    private String role;
}
