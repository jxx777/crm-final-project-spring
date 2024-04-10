package itschool.crmfinalproject.comments.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CommentBaseDTO(
        @Schema(description = "The ID of the entity this comment is associated with.", example = "123") String entityId,
        @Schema(description = "The type of the entity this comment is associated with.", example = "event") String entityType,
        @Schema(description = "The author of the comment.", example = "John Doe") String author,
        @Schema(description = "The text content of the comment.", example = "This is a sample comment.") String text,
        @Schema(description = "A list of attachment URLs for the comment.", example = "[\"http://example.com/attachment1.txt\", \"http://example.com/attachment2.txt\"]") List<String> attachments
) {
}