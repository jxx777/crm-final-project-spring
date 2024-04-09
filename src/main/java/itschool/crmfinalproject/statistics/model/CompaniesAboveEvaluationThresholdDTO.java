package itschool.crmfinalproject.statistics.model;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO representing the number of companies in a sector that have evaluations above a certain threshold.
 */
public record CompaniesAboveEvaluationThresholdDTO(
        @Schema(description = "Name of the sector") String sectorName,
        @Schema(description = "Count of companies with evaluations above a certain threshold") Long companyCount
) {
}
