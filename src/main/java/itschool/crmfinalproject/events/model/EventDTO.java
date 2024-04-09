package itschool.crmfinalproject.events.model;

import itschool.crmfinalproject.contacts.model.ContactBaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Schema(description = "Data Transfer Object for Event entity.")
public record EventDTO(
        @Schema(description = "The unique identifier of the event.", example = "6613d4d9efd22626872dc967") String id,
        @Schema(description = "The title of the event.", example = "Annual General Meeting") String title,
        @Schema(description = "The time at which the event is scheduled to occur.", example = "2023-04-05T10:15:30") LocalDateTime time,
        @Schema(description = "A description of the event, providing additional details.", example = "This is a detailed description of the event.") String description,
        @Schema(description = "The category of the event, categorizing the event for easier management and lookup.", example = "Meeting") String eventCategory,
        @Schema(description = "A map containing specific details of the event. The keys represent the field names, and the values are the corresponding details.", example = "{\"Location\": \"New York\", \"Speaker\": \"John Doe\"}") Map<String, Object> fieldDetails,
        @Schema(description = "A list of identifiers for comments associated with the event.", example = "[\"6613d4d9efd22626872dc995\", \"6613d4d9efd22626872dc995\"]") List<String> commentIds,
        @Schema(description = "A set of contacts associated with the event, represented as ContactBaseDTOs.", example = "[{\"id\": \"420\", \"name\": \"Jane Doe\"}]") Set<ContactBaseDTO> contacts) {
}