package itschool.crmfinalproject.contacts.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record NewContactDTO(
        @Schema(description = "The email address of the contact", example = "new@example.com") String email,
        @Schema(description = "The first name of the contact", example = "John") String firstName,
        @Schema(description = "The last name of the contact", example = "Doe") String lastName,
        @Schema(description = "The phone number of the contact", example = "555-555-555") String phoneNumber
) {}