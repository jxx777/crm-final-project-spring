package itschool.crmfinalproject.statistics.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO representing the total income generated by companies within each sector.
 */
public record TotalIncomePerSectorDTO(
        @Schema(description = "Name of the sector") String sectorName,
        @Schema(description = "Total income generated in the sector") Double totalIncome
) {
}
