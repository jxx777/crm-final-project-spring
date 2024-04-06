package itschool.crmfinalproject.entity.app.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "comments")
@Getter
@Setter
@Schema(description = "Represents a comment in the application.")
public class Comment {
    @Id
    @Schema(description = "The unique identifier of the comment.", example = "6602fc2bb6332a392f0deac4")
    private String id;

    @Schema(description = "The event ID to which the comment is associated.", example = "6602fc2bb6332a392f0deac9")
    private String eventId;

    @Schema(description = "The author of the comment.", example = "John Doe")
    private String author;

    @Schema(description = "The text content of the comment.", example = "This is a sample comment.")
    private String text;

    @Schema(description = "The timestamp when the comment was created.")
    private LocalDateTime timestamp;

    @Schema(description = "A set of user IDs who liked the comment.")
    private Set<String> likes = new HashSet<>();

    @Schema(description = "A list of attachment URLs for the comment.")
    private List<String> attachments;

    @Schema(description = "The ID of the parent comment, null if it's a top-level comment.", example = "null or 6602fc2bb6332a392f0deac4")
    private String parentId;

    @DBRef
    @Schema(description = "A list of replies to this comment.")
    private List<Comment> replies = new ArrayList<>();

    public void likeComment(String userId) {
        likes.add(userId);
    }

    public void unlikeComment(String userId) {
        likes.remove(userId);
    }

    public void addReply(Comment reply) {
        this.replies.add(reply);
    }

    public void removeReply(Comment reply) {
        this.replies.remove(reply);
    }
}