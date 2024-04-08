package itschool.crmfinalproject.model.user;

import itschool.crmfinalproject.entity.user.Role;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        Role role,
        LocalDateTime updatedAt,
        LocalDateTime createdAt,
        boolean accountNonExpired,
        boolean accountNonLocked,
        boolean credentialsNonExpired,
        boolean enabled,
        byte[] avatar
) {
}