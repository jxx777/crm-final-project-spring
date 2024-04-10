package itschool.crmfinalproject.comments.service;

import itschool.crmfinalproject.comments.document.Comment;
import itschool.crmfinalproject.comments.mapper.CommentMapper;
import itschool.crmfinalproject.comments.model.CommentBaseDTO;
import itschool.crmfinalproject.comments.model.CommentDTO;
import itschool.crmfinalproject.comments.repository.CommentRepository;
import itschool.crmfinalproject.events.document.Event;
import itschool.crmfinalproject.events.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;

    private final CommentMapper commentMapper;

    @Override
    public List<CommentDTO> findAllComments() {
        return commentRepository.findAll().stream()
                .map(commentMapper::toCommentDTO)
                .toList();
    }

    @Override
    public List<CommentDTO> findCommentsByAuthor(String author) {
        List<Comment> comments = commentRepository.findByAuthor(author);
        return comments.stream()
                .map(commentMapper::toCommentDTO)
                .toList();
    }

    @Override
    public List<CommentDTO> findCommentsByEventId(String eventId) {
        List<Comment> comments = commentRepository.findByEventId(eventId);
        return comments.stream()
                .map(commentMapper::toCommentDTO)
                .toList();
    }

    @Override
    public CommentDTO findCommentById(String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        return commentMapper.toCommentDTO(comment);
    }

    @Override
    public CommentDTO addComment(CommentBaseDTO commentDetails) {
        Comment comment = new Comment();
        comment.setEventId(commentDetails.entityId());
        comment.setAuthor(commentDetails.author());
        comment.setText(commentDetails.text());
        comment.setTimestamp(LocalDateTime.now());
        comment = commentRepository.save(comment);

        Event event = eventRepository.findById(comment.getEventId()).orElseThrow(() -> new RuntimeException("Event not found"));
        event.getCommentIds().add(comment.getId());
        eventRepository.save(event);

        return commentMapper.toCommentDTO(comment);
    }

    @Override
    public CommentDTO replyToComment(String parentId, CommentBaseDTO replyDetails) {
        Comment parentComment = commentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));

        Comment reply = new Comment();
        reply.setParentId(parentId);
        reply.setAuthor(replyDetails.author());
        reply.setText(replyDetails.text());
        reply.setTimestamp(LocalDateTime.now());
        reply = commentRepository.save(reply);  // Save the reply comment
        parentComment.getReplies().add(reply);
        commentRepository.save(parentComment);  // Save the parent comment
        return commentMapper.toCommentDTO(reply);
    }

    @Override
    public CommentDTO addLike(String commentId, String userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.likeComment(userId);
        comment = commentRepository.save(comment);
        return commentMapper.toCommentDTO(comment);
    }

    @Override
    public CommentDTO removeLike(String commentId, String userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.unlikeComment(userId);
        comment = commentRepository.save(comment);
        return commentMapper.toCommentDTO(comment);
    }


    @Override
    public void deleteComment(String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        deleteReplies(comment);
        commentRepository.deleteById(commentId);
    }

    private void deleteReplies(Comment comment) {
        for (Comment reply : comment.getReplies()) {
            deleteReplies(reply);
            commentRepository.deleteById(reply.getId());
        }
    }
}