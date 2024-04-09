package itschool.crmfinalproject.model.analysis;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data transfer object that aggregates various data points across the system.
 */
public record AggregatedDataDTO(
        @Schema(description = "Total number of events") int totalEvents,
        @Schema(description = "Total number of contacts") int totalContacts,
        @Schema(description = "Total income generated from events") double totalIncome,
        @Schema(description = "Average number of contacts per event") double averageContactsPerEvent
) {}