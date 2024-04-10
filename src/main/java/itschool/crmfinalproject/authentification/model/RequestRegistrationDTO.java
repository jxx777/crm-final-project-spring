package itschool.crmfinalproject.authentification.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for user registration requests.
 */
public record RequestRegistrationDTO(
        @Schema(description = "Username for the new account", example = "newuser") String username,
        @Schema(description = "Email address for the new account", example = "new@example.com") String email,
        @Schema(description = "Password for the new account", example = "newpassword") String password,
        @Schema(description = "Password confirmation for the new account", example = "newpassword") String confirmPassword,
        @Schema(description = "First name of the user", example = "John") String firstName,
        @Schema(description = "Last name of the user", example = "Doe") String lastName,
        @Schema(description = "Phone number of the user", example = "555-555-555") String phoneNumber
) {
}
