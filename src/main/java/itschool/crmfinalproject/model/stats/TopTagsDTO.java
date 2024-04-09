package itschool.crmfinalproject.model.stats;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO representing the most used tags and their counts.
 */
public record TopTagsDTO(
        @Schema(description = "The tag") String tag,
        @Schema(description = "Count of how many times the tag is used") Long usageCount
) {
}
