package itschool.crmfinalproject.companies.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Basic Data Transfer Object representing essential company information.
 */
public record CompanyBaseDTO(
        @Schema(description = "Unique identifier of the company") Long id,
        @Schema(description = "Name of the company") String name,
        @Schema(description = "Evaluation metric of the company, possibly representing market value or an internal assessment") Double evaluation,
        @Schema(description = "Total income generated from the company") Double incomeFromCompany
) {
}