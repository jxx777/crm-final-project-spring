package itschool.crmfinalproject.model.comment;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;


public record CommentBaseDTO(
        @Schema(description = "The author of the comment.", example = "John Doe") String author,
        @Schema(description = "The text content of the comment.", example = "This is a sample comment.") String text,
        @Schema(description = "A list of attachment URLs for the comment.", example = "thisIsAString.txt") List<String> attachments
) {
}