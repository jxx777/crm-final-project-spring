package itschool.crmfinalproject.model.auth;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for user registration requests.
 */
public record RequestRegistrationDTO(
        @Schema(description = "Username for the new account") String username,
        @Schema(description = "Email address for the new account") String email,
        @Schema(description = "Password for the new account") String password,
        @Schema(description = "Password confirmation for the new account") String confirmPassword,
        @Schema(description = "First name of the user") String firstName,
        @Schema(description = "Last name of the user") String lastName,
        @Schema(description = "Phone number of the user") String phoneNumber
) {
}
