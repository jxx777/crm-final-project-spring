package itschool.crmfinalproject.model.user;

import itschool.crmfinalproject.entity.user.Role;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String username,
        String email,
        Role role,
        LocalDateTime updatedAt,
        LocalDateTime createdAt
) { }