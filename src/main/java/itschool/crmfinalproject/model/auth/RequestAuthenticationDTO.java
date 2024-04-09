package itschool.crmfinalproject.model.auth;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for user authentication requests.
 */
public record RequestAuthenticationDTO(
        // Username used for authentication
        @Schema(description = "The username for authentication") String username,

        // Password used for authentication
        @Schema(description = "The password for authentication") String password
) {
}
