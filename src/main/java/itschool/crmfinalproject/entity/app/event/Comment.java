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
    private String id;

    private String eventId;

    private String author;

    private String text;

    private LocalDateTime timestamp;

    private Set<String> likes = new HashSet<>();

    private List<String> attachments;

    private String parentId;

    @DBRef
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