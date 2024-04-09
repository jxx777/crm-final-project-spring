package itschool.crmfinalproject.companies.model;

import io.swagger.v3.oas.annotations.media.Schema;
import itschool.crmfinalproject.contacts.model.ContactBaseDTO;

import java.util.List;

/**
 * Detailed Data Transfer Object representing a company, including associated contacts.
 */
public record CompanyDTO(
        @Schema(description = "Unique identifier of the company") Long id,
        @Schema(description = "Name of the company") String name,
        @Schema(description = "Evaluation metric of the company, possibly representing market value or an internal assessment") Double evaluation,
        @Schema(description = "Total income generated from the company") Double incomeFromCompany,
        @Schema(description = "List of contacts associated with the company, represented as ContactBaseDTOs") List<ContactBaseDTO> contacts
) {
}