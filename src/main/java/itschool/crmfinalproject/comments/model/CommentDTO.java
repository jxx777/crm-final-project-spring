package itschool.crmfinalproject.comments.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public record CommentDTO(
        @Schema(description = "The unique identifier of the comment.", example = "6602fc2bb6332a392f0deac4") String id,
        @Schema(description = "The event ID to which the comment is associated.", example = "6602fc2bb6332a392f0deac9") String eventId,
        @Schema(description = "The author of the comment.", example = "John Doe") String author,
        @Schema(description = "The text content of the comment.", example = "This is a sample comment.") String text,
        @Schema(description = "The timestamp when the comment was created.") LocalDateTime timestamp,
        @Schema(description = "A set of user IDs who liked the comment.") Set<String> likes,
        @Schema(description = "A list of attachment URLs for the comment.") List<String> attachments,
        @Schema(description = "The ID of the parent comment, null if it's a top-level comment.", example = "null or 6602fc2bb6332a392f0deac4") String parentId,
        @Schema(description = "A list of replies to this comment.") List<CommentDTO> replies) {
}