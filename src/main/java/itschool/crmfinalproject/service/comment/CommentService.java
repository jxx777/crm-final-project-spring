package itschool.crmfinalproject.service.comment;

import itschool.crmfinalproject.entity.app.event.Comment;
import java.util.List;

/**
 * Service interface for managing comments in the application.
 */
public interface CommentService {

    /**
     * Find all comments in the system.
     *
     * @return a list of all comments
     */
    List<Comment> findAllComments();

    /**
     * Find comments made by a specific author.
     *
     * @param author the author's name
     * @return a list of comments made by the specified author
     */
    List<Comment> findCommentsByAuthor(String author);

    /**
     * Find comments associated with a specific event.
     *
     * @param eventId the ID of the event
     * @return a list of comments associated with the specified event
     */
    List<Comment> findCommentsByEventId(String eventId);

    /**
     * Post a new comment for an event.
     *
     * @param eventId the ID of the event to comment on
     * @param author the author of the comment
     * @param text the comment text
     * @param attachments a list of attachment identifiers
     * @return the newly created comment
     */
    Comment postComment(String eventId, String author, String text, List<String> attachments);

    /**
     * Add a reply to an existing comment.
     *
     * @param parentId the ID of the comment being replied to
     * @param reply the reply comment details
     * @return the newly created reply comment
     */
    Comment addReply(String parentId, Comment reply);

    /**
     * Add a like to a comment.
     *
     * @param commentId the ID of the comment to like
     * @param userId the ID of the user liking the comment
     * @return the updated comment with the new like
     */
    Comment addLike(String commentId, String userId);

    /**
     * Remove a like from a comment.
     *
     * @param commentId the ID of the comment to unlike
     * @param userId the ID of the user unliking the comment
     * @return the updated comment with the like removed
     */
    Comment removeLike(String commentId, String userId);

    /**
     * Delete a specific comment by its ID.
     *
     * @param id the ID of the comment to delete
     */
    void deleteComment(String id);

    /**
     * Retrieve a comment by its ID.
     *
     * @param id the ID of the comment to retrieve
     * @return the requested comment
     */
    Comment getCommentById(String id);
}