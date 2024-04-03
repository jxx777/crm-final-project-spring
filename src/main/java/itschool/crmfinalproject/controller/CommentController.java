package itschool.crmfinalproject.controller;

import itschool.crmfinalproject.entity.app.event.Comment;
import itschool.crmfinalproject.service.comment.CommentService;
import itschool.crmfinalproject.service.comment.CommentServiceImpl;
import itschool.crmfinalproject.utility.GenerateResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name = "Comment Controller", description = "Controller for handling operations related to comments.")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentServiceImpl commentServiceImpl) {
        this.commentService = commentServiceImpl;
    }

    @Operation(summary = "Get all comments", description = "Fetch all comments from the database.")
    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.findAllComments();
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Get comments by author", description = "Fetch comments made by a specific author.")
    @GetMapping("/author/{author}")
    public ResponseEntity<List<Comment>> getCommentsByAuthor(@PathVariable String author) {
        List<Comment> comments = commentService.findCommentsByAuthor(author);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Get comments for an event", description = "Fetch comments associated with a specific event.")
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Comment>> getCommentsByEventId(@PathVariable String eventId) {
        List<Comment> comments = commentService.findCommentsByEventId(eventId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Post a comment", description = "Post a new comment for an event.")
    @PostMapping("/add/{eventId}")
    public ResponseEntity<Comment> postComment(@PathVariable String eventId, @RequestBody Comment commentDetails) {
        Comment comment = commentService.postComment(eventId,
                commentDetails.getAuthor(),
                commentDetails.getText(),
                commentDetails.getAttachments());
        return ResponseEntity.ok(comment);
    }

    @Operation(summary = "Add a reply to a comment", description = "Post a reply to an existing comment.")
    @PostMapping("/add-reply/{parentId}")
    public ResponseEntity<Comment> addReply(@PathVariable String parentId, @RequestBody Comment replyDetails) {
        Comment reply = commentService.addReply(parentId, replyDetails);
        return ResponseEntity.ok(reply);
    }

    @Operation(summary = "Add a like to a comment", description = "Add a like to a specific comment by a user.")
    @PatchMapping("/add-like/{commentId}/{userId}")
    public ResponseEntity<Comment> addLike(@PathVariable String commentId, @PathVariable String userId) {
        Comment likedComment = commentService.addLike(commentId, userId);
        return ResponseEntity.ok(likedComment);
    }

    @Operation(summary = "Remove a like from a comment", description = "Remove a like from a specific comment by a user.")
    @PatchMapping("/remove-like/{commentId}/{userId}")
    public ResponseEntity<Comment> removeLike(@PathVariable String commentId, @PathVariable String userId) {
        Comment unlikedComment = commentService.removeLike(commentId, userId);
        return ResponseEntity.ok(unlikedComment);
    }

    @Operation(summary = "Delete a comment", description = "Delete a specific comment from the database.")
    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable String id) {
        commentService.deleteComment(id);
        return GenerateResponse.success("Comment deleted", null);
    }

    @Operation(summary = "Get a comment by ID", description = "Fetch a specific comment using its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable String id) {
        Comment comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }
}