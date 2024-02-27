package com.example.UserManagementMicroservice.controllers;

import com.example.UserManagementMicroservice.dtos.UpdateUserDto;
import com.example.UserManagementMicroservice.dtos.UserDto;
import com.example.UserManagementMicroservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class UserController {
    private final UserService userService;

    @GetMapping("/user-list")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> dtos = userService.getUsers();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/role-list/{role}")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable String role) {
        List<UserDto> dtos = userService.getUsersByRole(role);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/by-email/{email}")
    public UUID getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable UUID userId, @RequestBody UpdateUserDto givenDto) {
        return ResponseEntity.ok(userService.updateUser(userId, givenDto));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }
}
