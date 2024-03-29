package itschool.crmfinalproject.service.comment;

import itschool.crmfinalproject.entity.app.event.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAllComments();

    List<Comment> findCommentsByAuthor(String author);

    List<Comment> findCommentsByEventId(String eventId);

    Comment postComment(String eventId, String author, String text, List<String> attachments);

    Comment addReply(String parentId, Comment reply);

    Comment addLike(String commentId, String userId);

    Comment removeLike(String commentId, String userId);

    void deleteComment(String id);

    Comment getCommentById(String id);
}
