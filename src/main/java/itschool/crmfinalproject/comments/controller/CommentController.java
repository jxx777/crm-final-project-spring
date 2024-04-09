package itschool.crmfinalproject.comments.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.comments.document.Comment;
import itschool.crmfinalproject.comments.model.CommentBaseDTO;
import itschool.crmfinalproject.comments.model.CommentDTO;
import itschool.crmfinalproject.comments.service.CommentService;
import itschool.crmfinalproject.common.utility.GenerateResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Tag(name = "Comment Service", description = "Manages comment-related operations, including creation, retrieval, and moderation.")
public class CommentController {

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable String commentId) {
        return ResponseEntity.ok(commentService.findCommentById(commentId));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all comments", description = "Fetch all comments from the database.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all comments", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        return ResponseEntity.ok(commentService.findAllComments());
    }

    @GetMapping("/author/{authorUsername}")
    @Operation(summary = "Get comments by author", description = "Fetch comments made by a specific author.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved comments for the author", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    public ResponseEntity<List<CommentDTO>> getCommentsByAuthor(@PathVariable @Parameter(description = "Author whose comments are to be fetched") String authorUsername) {
        List<CommentDTO> comments = commentService.findCommentsByAuthor(authorUsername);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get comments for an event", description = "Fetch comments associated with a specific event.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved comments for the event", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    public ResponseEntity<CommentDTO> getCommentByEventId(@PathVariable String eventId) {
        CommentDTO comment = commentService.findCommentById(eventId);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/{eventId}")
    @Operation(summary = "Post a comment", description = "Post a new comment for an event.")
    @ApiResponse(responseCode = "200", description = "Successfully posted a new comment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    public ResponseEntity<CommentDTO> addComment(@PathVariable String eventId, @RequestBody CommentBaseDTO commentDetails) {
        return ResponseEntity.ok(commentService.addComment(eventId, commentDetails));
    }

    @PostMapping("/reply/{parentId}")
    @Operation(summary = "Add a reply to a comment", description = "Post a reply to an existing comment.")
    @ApiResponse(responseCode = "200", description = "Successfully added a reply to the comment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    public ResponseEntity<CommentDTO> replyToComment(@PathVariable String parentId, @RequestBody CommentBaseDTO replyDetails) {
        return ResponseEntity.ok(commentService.replyToComment(parentId, replyDetails));
    }

    @PatchMapping("/add-like/{commentId}/{userId}")
    @Operation(summary = "Add a like to a comment", description = "Add a like to a specific comment by a user.")
    @ApiResponse(responseCode = "200", description = "Successfully added a like to the comment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    public ResponseEntity<CommentDTO> addLike(
            @PathVariable @Parameter(description = "Comment ID to add a like to") String commentId,
            @PathVariable @Parameter(description = "User ID of the user liking the comment") String userId) {
        CommentDTO likedComment = commentService.addLike(commentId, userId);
        return ResponseEntity.ok(likedComment);
    }

    @PatchMapping("/remove-like/{commentId}/{userId}")
    @Operation(summary = "Remove a like from a comment", description = "Remove a like from a specific comment by a user.")
    @ApiResponse(responseCode = "200", description = "Successfully removed a like from the comment", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Comment.class))})
    public ResponseEntity<CommentDTO> removeLike(
            @PathVariable @Parameter(description = "Comment ID to remove a like from") String commentId,
            @PathVariable @Parameter(description = "User ID of the user removing the like") String userId
    ) {
        CommentDTO unlikedComment = commentService.removeLike(commentId, userId);
        return ResponseEntity.ok(unlikedComment);
    }

    @DeleteMapping("/{commentId}")
    @SneakyThrows
    @Operation(summary = "Delete a comment", description = "Delete a specific comment from the database.")
    @ApiResponse(responseCode = "200", description = "Successfully deleted the comment")
    public ResponseEntity<?> deleteComment(@PathVariable @Parameter(description = "Comment ID to delete") String commentId) {
        commentService.deleteComment(commentId);
        return GenerateResponse.success("Comment deleted", null);
    }

    private final CommentService commentService;
}