package itschool.crmfinalproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.entity.app.event.Comment;
import itschool.crmfinalproject.service.comment.CommentService;
import itschool.crmfinalproject.utility.GenerateResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name = "Comment Service", description = "Manages comment-related operations, including creation, retrieval, and moderation.")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Get all comments", description = "Fetch all comments from the database.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all comments", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.findAllComments();
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Get comments by author", description = "Fetch comments made by a specific author.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved comments for the author", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    @GetMapping("/author/{author}")
    public ResponseEntity<List<Comment>> getCommentsByAuthor(@PathVariable @Parameter(description = "Author whose comments are to be fetched") String author) {
        List<Comment> comments = commentService.findCommentsByAuthor(author);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Get comments for an event", description = "Fetch comments associated with a specific event.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved comments for the event", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Comment>> getCommentsByEventId(@PathVariable @Parameter(description = "Event ID to fetch comments for") String eventId) {
        List<Comment> comments = commentService.findCommentsByEventId(eventId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Post a comment", description = "Post a new comment for an event.")
    @ApiResponse(responseCode = "200", description = "Successfully posted a new comment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    @PostMapping("/add/{eventId}")
    public ResponseEntity<Comment> postComment(@PathVariable @Parameter(description = "Event ID to add a comment to") String eventId, @RequestBody Comment commentDetails) {
        Comment comment = commentService.postComment(eventId, commentDetails.getAuthor(), commentDetails.getText(), commentDetails.getAttachments());
        return ResponseEntity.ok(comment);
    }

    @Operation(summary = "Add a reply to a comment", description = "Post a reply to an existing comment.")
    @ApiResponse(responseCode = "200", description = "Successfully added a reply to the comment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    @PostMapping("/add-reply/{parentId}")
    public ResponseEntity<Comment> addReply(@PathVariable @Parameter(description = "Parent comment ID to reply to") String parentId, @RequestBody Comment replyDetails) {
        Comment reply = commentService.addReply(parentId, replyDetails);
        return ResponseEntity.ok(reply);
    }

    @Operation(summary = "Add a like to a comment", description = "Add a like to a specific comment by a user.")
    @ApiResponse(responseCode = "200", description = "Successfully added a like to the comment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    @PatchMapping("/add-like/{commentId}/{userId}")
    public ResponseEntity<Comment> addLike(@PathVariable @Parameter(description = "Comment ID to add a like to") String commentId, @PathVariable @Parameter(description = "User ID of the user liking the comment") String userId) {
        Comment likedComment = commentService.addLike(commentId, userId);
        return ResponseEntity.ok(likedComment);
    }

    @Operation(summary = "Remove a like from a comment", description = "Remove a like from a specific comment by a user.")
    @ApiResponse(responseCode = "200", description = "Successfully removed a like from the comment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    @PatchMapping("/remove-like/{commentId}/{userId}")
    public ResponseEntity<Comment> removeLike(@PathVariable @Parameter(description = "Comment ID to remove a like from") String commentId, @PathVariable @Parameter(description = "User ID of the user removing the like") String userId) {
        Comment unlikedComment = commentService.removeLike(commentId, userId);
        return ResponseEntity.ok(unlikedComment);
    }

    @SneakyThrows
    @Operation(summary = "Delete a comment", description = "Delete a specific comment from the database.")
    @ApiResponse(responseCode = "200", description = "Successfully deleted the comment")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable @Parameter(description = "Comment ID to delete") String commentId) {
        commentService.deleteComment(commentId);
        return GenerateResponse.success("Comment deleted", null);
    }

    @Operation(summary = "Get a comment by ID", description = "Fetch a specific comment using its ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the comment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable @Parameter(description = "Comment ID to retrieve") String id) {
        Comment comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }
}