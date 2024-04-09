package itschool.crmfinalproject.statistics.model;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO representing the number of contacts associated with each company.
 */
public record ContactsPerCompanyDTO(
        @Schema(description = "Name of the company") String companyName,
        @Schema(description = "Count of contacts associated with the company") Long contactCount
) {
}
