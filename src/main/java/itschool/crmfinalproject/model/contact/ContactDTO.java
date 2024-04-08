package itschool.crmfinalproject.model.contact;

import io.swagger.v3.oas.annotations.media.Schema;
import itschool.crmfinalproject.entity.app.Address;
import itschool.crmfinalproject.model.company.CompanyBaseDTO;

import java.time.LocalDateTime;
import java.util.Set;

public record ContactDTO(
        @Schema(description = "Creation time of the contact") LocalDateTime createdAt,
        @Schema(description = "Update time of the contact") LocalDateTime updatedAt,
        @Schema(description = "User who created the contact") String createdBy,
        @Schema(description = "User who last updated the contact") String updatedBy,
        @Schema(description = "The ID of the contact", example = "1") Long id,
        @Schema(description = "The first name of the contact", example = "John") String firstName,
        @Schema(description = "The last name of the contact", example = "Doe") String lastName,
        @Schema(description = "The email address of the contact", example = "john.doe@example.com") String email,
        @Schema(description = "The phone number of the contact", example = "+1234567890") String phoneNumber,
        @Schema(description = "The company associated with the contact") CompanyBaseDTO company,
        @Schema(description = "The position of the contact at the company", example = "Software Engineer") String position,
        @Schema(description = "The address of the contact") Address address,
        @Schema(description = "A brief description of the contact", example = "A key figure in the IT department.") String description,
        @Schema(description = "Tags associated with the contact", example = "[\"VIP\", \"Priority\"]") Set<String> tags
) {
}