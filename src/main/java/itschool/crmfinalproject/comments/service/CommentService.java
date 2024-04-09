package itschool.crmfinalproject.comments.service;

import itschool.crmfinalproject.comments.model.CommentBaseDTO;
import itschool.crmfinalproject.comments.model.CommentDTO;

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
    List<CommentDTO> findAllComments();


    /**
     * Find comments made by a specific author.
     *
     * @param author the author's name
     * @return a list of comments made by the specified author
     */
    List<CommentDTO> findCommentsByAuthor(String author);

    /**
     * Find comments associated with a specific event.
     *
     * @param eventId the ID of the event
     * @return a list of comments associated with the specified event
     */
    List<CommentDTO> findCommentsByEventId(String eventId);

    /**
     * Post a new comment for an event.
     *
     * @param eventId the ID of the event to comment on
     * @param commentDetails: new comment fieldDetails
     * @return the newly created comment
     */
    CommentDTO addComment(String eventId, CommentBaseDTO commentDetails);
    /**
     * Add a reply to an existing comment.
     *
     * @param parentId the ID of the comment being replied to
     * @param replyDetails the reply comment fieldDetails
     * @return the newly created reply comment
     */
    CommentDTO replyToComment(String parentId, CommentBaseDTO replyDetails);

    /**
     * Add a like to a comment.
     *
     * @param commentId the ID of the comment to like
     * @param userId    the ID of the user liking the comment
     * @return the updated comment with the new like
     */
    CommentDTO addLike(String commentId, String userId);

    /**
     * Remove a like from a comment.
     *
     * @param commentId the ID of the comment to unlike
     * @param userId    the ID of the user unliking the comment
     * @return the updated comment with the like removed
     */
    CommentDTO removeLike(String commentId, String userId);

    /**
     * Delete a specific comment by its ID.
     *
     * @param commentId the ID of the comment to delete
     */
    void deleteComment(String commentId);

    /**
     * Retrieve a comment by its ID.
     *
     * @param commentId the ID of the comment to retrieve
     * @return the requested comment
     */
    CommentDTO findCommentById(String commentId);
}