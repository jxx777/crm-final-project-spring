package itschool.crmfinalproject.sectors.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO representing the basic information about a business or industry sector.
 */
public record SectorBaseDTO(
        @Schema(description = "Unique identifier of the sector") Long id,
        @Schema(description = "Name of the sector") String sectorName
) {
}