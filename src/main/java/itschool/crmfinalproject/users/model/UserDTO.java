package itschool.crmfinalproject.users.model;

import io.swagger.v3.oas.annotations.media.Schema;
import itschool.crmfinalproject.users.entity.Role;

import java.time.LocalDateTime;

/**
 * DTO for detailed user information, including roles and account status.
 */
public record UserDTO(
        @Schema(description = "Unique identifier of the user") Long id,
        @Schema(description = "Username of the user") String username,
        @Schema(description = "Email address of the user") String email,
        @Schema(description = "First name of the user") String firstName,
        @Schema(description = "Last name of the user") String lastName,
        @Schema(description = "Phone number of the user") String phoneNumber,
        @Schema(description = "Role of the user") Role role,
        @Schema(description = "Timestamp of the last update to the user's information") LocalDateTime updatedAt,
        @Schema(description = "Timestamp when the user's information was created") LocalDateTime createdAt,
        @Schema(description = "Flag indicating whether the user's account has expired") boolean accountNonExpired,
        @Schema(description = "Flag indicating whether the user's account is locked") boolean accountNonLocked,
        @Schema(description = "Flag indicating whether the user's credentials have expired") boolean credentialsNonExpired,
        @Schema(description = "Flag indicating whether the user's account is enabled") boolean enabled,
        @Schema(description = "Avatar image data for the user") byte[] avatar
) {
}
