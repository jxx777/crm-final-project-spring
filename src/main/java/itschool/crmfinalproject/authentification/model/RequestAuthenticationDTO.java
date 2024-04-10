package itschool.crmfinalproject.authentification.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for user authentication requests.
 */
public record RequestAuthenticationDTO(
        @Schema(description = "The username for authentication", example = "username") String username,
        @Schema(description = "The password for authentication", example = "password") String password
) {
}
