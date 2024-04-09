package itschool.crmfinalproject.statistics.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO representing the average company evaluation within each sector.
 */
public record AvgCompanyEvaluationPerSectorDTO(
        @Schema(description = "Name of the sector") String sectorName,
        @Schema(description = "Average evaluation of companies in the sector") Double averageEvaluation
) {
}