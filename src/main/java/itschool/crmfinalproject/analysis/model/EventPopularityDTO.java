package itschool.crmfinalproject.analysis.model;

/**
 * Represents the popularity of an event by counting interactions or participation in a specific month and year.
 */
public record EventPopularityDTO(String eventId, Integer month, Integer year, Integer count) {
}
