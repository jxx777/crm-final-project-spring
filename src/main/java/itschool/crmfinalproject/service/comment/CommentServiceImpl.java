package itschool.crmfinalproject.service.comment;

import itschool.crmfinalproject.entity.app.event.Comment;
import itschool.crmfinalproject.repository.CommentRepository;
import itschool.crmfinalproject.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Comment comment = new Comment();
        comment.setEventId(eventId);
        comment.setAuthor(author);
        comment.setText(text);
        comment.setTimestamp(java.time.LocalDateTime.now());
        comment.setAttachments(attachments);
        return commentRepository.insert(comment);
    }

    @Override
    public Comment addReply(String parentId, Comment reply) {
        Comment parentComment = commentRepository.findById(parentId).orElseThrow(() -> new RuntimeException("Comment not found with ID: " + parentId));

        // Create a new Comment object for the reply to ensure it's a separate entity
        Comment replyComment = new Comment();
        replyComment.setParentId(parentId); // Set the parent ID to link the reply to its parent
//        replyComment.setEventId(parentComment.getEventId()); // Set the event ID for the reply
        replyComment.setAuthor(reply.getAuthor());
        replyComment.setText(reply.getText());
        replyComment.setTimestamp(java.time.LocalDateTime.now()); // Set the current timestamp for the reply
        replyComment.setAttachments(reply.getAttachments()); // Set attachments for the reply if any

        // Save the reply to the database to generate its ID
        replyComment = commentRepository.save(replyComment);

        // Now that the reply has an ID, add it to the parent comment's replies and save the parent comment
        parentComment.addReply(replyComment);
        return commentRepository.save(parentComment);
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

        deleteReplies(comment);
        commentRepository.deleteById(id);
    }

    private void deleteReplies(Comment parentComment) {
        parentComment.getReplies().forEach(reply -> {
            deleteReplies(reply);
            commentRepository.deleteById(reply.getId());
        });
    }

    @Override
    public Comment getCommentById(String id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found with ID: " + id));
    }
}