package itschool.crmfinalproject.contacts.model;

import io.swagger.v3.oas.annotations.media.Schema;

public record ContactBaseDTO(
        @Schema(description = "The ID of the contact", example = "") Long id,
        @Schema(description = "The email address of the contact", example = "john.doe@example.com") String email,
        @Schema(description = "The first name of the contact", example = "John") String firstName,
        @Schema(description = "The last name of the contact", example = "Doe") String lastName,
        @Schema(description = "The position of the contact at the company", example = "Software Engineer") String position,
        @Schema(description = "The phone number of the contact", example = "+1234567890") String phoneNumber
) {
}
