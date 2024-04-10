package itschool.crmfinalproject.events.model;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Data Transfer Object for Event entity.")
public record NewEventDTO(
        @Schema(description = "The title of the event.", example = "Annual General Meeting") String title,
        @Schema(description = "A description of the event, providing additional details.", example = "This is a detailed description of the event.") String description,
        @Schema(description = "The category of the event, categorizing the event for easier management and lookup.", example = "Meeting") String eventCategory)
{}