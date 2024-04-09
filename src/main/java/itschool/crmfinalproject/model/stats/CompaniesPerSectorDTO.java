package itschool.crmfinalproject.model.stats;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO representing the number of companies in each sector.
 */
public record CompaniesPerSectorDTO(
        @Schema(description = "Name of the sector") String sectorName,
        @Schema(description = "Count of companies in the sector") Long companyCount
) {
}
