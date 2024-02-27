package com.example.UserManagementMicroservice.repositories;

import com.example.UserManagementMicroservice.models.Role;
import com.example.UserManagementMicroservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
}
