package itschool.crmfinalproject.model.stats;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO representing sectors with the highest number of companies.
 */
public record TopSectorsByCompanyCountDTO(
        @Schema(description = "Name of the sector") String sectorName,
        @Schema(description = "Count of companies in the sector") Long companyCount
) {
}
