package itschool.crmfinalproject.entity.app.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Document(collection = "comments")
@Getter
@Setter
public class Comment {
    @Id
    private String id;

    private String eventId;

    private String author;

    private String text;

    private LocalDateTime timestamp;

    private Set<String> likes = new HashSet<>(); // Initialize the set here

    private List<String> attachments;  // List of attachment URLs

    private String parentId; // ID of the parent comment, null if top-level

    @DBRef
    private List<Comment> replies = new ArrayList<>(); // Replies to this comment

    public void likeComment(String userId) {
        likes.add(userId);
    }

    public void unlikeComment(String userId) {
        likes.remove(userId);
    }

    public void addReply(Comment reply) {
        if (this.replies == null) {
            this.replies = new ArrayList<>();
        }
        this.replies.add(reply);
    }
}