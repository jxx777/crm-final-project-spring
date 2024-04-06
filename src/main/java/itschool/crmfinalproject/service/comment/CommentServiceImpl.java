package itschool.crmfinalproject.service.comment;

import itschool.crmfinalproject.entity.app.event.Comment;
import itschool.crmfinalproject.entity.app.event.Event;
import itschool.crmfinalproject.repository.CommentRepository;
import itschool.crmfinalproject.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;

    @Override
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> findCommentsByAuthor(String author) {
        return commentRepository.findByAuthor(author);
    }

    @Override
    public List<Comment> findCommentsByEventId(String eventId) {
        return commentRepository.findByEventId(eventId).stream().sorted(Comparator.comparing(Comment::getTimestamp).reversed()).toList();
    }

    @Override
    public Comment postComment(String eventId, String author, String text, List<String> attachments) {
        // Create and save the new comment
        Comment comment = new Comment();
        comment.setEventId(eventId);

        comment.setAuthor(author);
        comment.setText(text);
        comment.setAttachments(attachments);

        comment.setTimestamp(LocalDateTime.now());
        comment = commentRepository.insert(comment);

        // Retrieve the associated event
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));

        event.getCommentIds().add(comment.getId());

        // Save the updated event
        eventRepository.save(event);

        return comment;
    }

    @Override
    public Comment addReply(String parentId, Comment reply) {
        Comment parentComment = commentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with ID: " + parentId));

        Comment replyComment = new Comment();
        replyComment.setParentId(parentId);
        replyComment.setEventId(parentComment.getEventId());
        replyComment.setAuthor(reply.getAuthor());
        replyComment.setText(reply.getText());
        replyComment.setTimestamp(LocalDateTime.now());
        replyComment.setAttachments(reply.getAttachments());

        commentRepository.save(replyComment);
        parentComment.addReply(replyComment);
        commentRepository.save(parentComment);

        return replyComment;
    }

    @Override
    public Comment addLike(String commentId, String userId) {
        return updateLikes(commentId, userId, true);
    }

    @Override
    public Comment removeLike(String commentId, String userId) {
        return updateLikes(commentId, userId, false);
    }

    private Comment updateLikes(String commentId, String userId, boolean add) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found with ID: " + commentId));

        if (add) {
            comment.likeComment(userId);
        } else {
            comment.unlikeComment(userId);
        }

        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(String id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with ID: " + id));

        // Checking if the comment is a toplevel comment (direct to event), or child of another comment (nested)
        if (comment.getParentId() != null) {
            Comment parentComment = commentRepository.findById(comment.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found with ID: " + comment.getParentId()));

            parentComment.removeReply(comment);
            commentRepository.save(parentComment);
        } else {
            Event event = eventRepository.findById(comment.getEventId())
                    .orElseThrow(() -> new RuntimeException("Event not found with ID: " + comment.getEventId()));

            event.getCommentIds().remove(comment.getId());
            eventRepository.save(event);
        }

        // Delete the comment and its replies recursively
        deleteReplies(comment);
        commentRepository.deleteById(id);
    }

    private void deleteReplies(Comment comment) {
        comment.getReplies().forEach(reply -> {
            deleteReplies(reply);  // Recursive deletion of nested replies
            commentRepository.deleteById(reply.getId());  // Delete the reply itself
        });
    }

    @Override
    public Comment getCommentById(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with ID: " + id));
    }
}