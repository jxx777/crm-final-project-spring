package itschool.crmfinalproject.model.analysis;

/**
 * Represents the total revenue generated by a sector, aggregating data across different sources.
 */
public record SectorRevenueDTO(
        String sectorName,
        double totalRevenue
) {}

