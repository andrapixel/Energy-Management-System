package com.example.UserManagementMicroservice.services;

import com.example.UserManagementMicroservice.dtos.UpdateUserDto;
import com.example.UserManagementMicroservice.dtos.UserDto;
import com.example.UserManagementMicroservice.dtos.mappers.UserMapper;
import com.example.UserManagementMicroservice.exceptions.UserNotFoundException;
import com.example.UserManagementMicroservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.UserManagementMicroservice.models.User;
import com.example.UserManagementMicroservice.models.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<UserDto> getUsers() {
        List<User> userList = userRepository.findAll();

        return userList.stream()
                .map(UserMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsersByRole(String role) {
        // check the entered role is a valid one
        if (!role.equals(Role.CLIENT.name()) && !role.equals(Role.ADMIN.name())) {
            throw new IllegalArgumentException("Invalid role.");
        }

        List<User> userList = userRepository.findByRole(Role.valueOf(role));

        return userList.stream()
                .map(UserMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(UUID userId) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException("User with ID = " + userId + " could not be found.");
        }

        return UserMapper.mapToDto(foundUser.get());
    }

    public UUID getUserByEmail(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException("No user with the email: " + email + " exists.");
        }

        return foundUser.get().getUserId();
    }

    public UserDto getUserDtoByEmail(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException("No user with the email: " + email + " exists.");
        }

        return UserMapper.mapToDto(foundUser.get());
    }

    public String updateUser(UUID userId, UpdateUserDto userDto) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException("User with ID = " + userId + " could not be found.");
        }

        User updatedUser = foundUser.get();
        updatedUser.setName(userDto.getName());
        updatedUser.setEmail(userDto.getEmail());
        userRepository.save(updatedUser);

        return "User details were updated successfully.";
    }

    public String deleteUser(UUID userId) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException("User with ID = " + userId + " could not be found.");
        }

        userRepository.deleteById(userId);
        return "User removed successfully.";
    }
}
