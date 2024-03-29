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

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentServiceImpl commentServiceImpl) {
        this.commentService = commentServiceImpl;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.findAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<Comment>> getCommentsByAuthor(@PathVariable String author) {
        List<Comment> comments = commentService.findCommentsByAuthor(author);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Comment>> getCommentsByEventId(@PathVariable String eventId) {
        List<Comment> comments = commentService.findCommentsByEventId(eventId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/add/{eventId}")
    public ResponseEntity<Comment> postComment(@PathVariable String eventId, @RequestBody Comment commentDetails) {
        Comment comment = commentService.postComment(eventId, commentDetails.getAuthor(), commentDetails.getText(), commentDetails.getAttachments());
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/add-reply/{parentId}")
    public ResponseEntity<Comment> addReply(@PathVariable String parentId, @RequestBody Comment replyDetails) {
        Comment reply = commentService.addReply(parentId, replyDetails);
        return ResponseEntity.ok(reply);
    }

    @PatchMapping("/add-like/{commentId}/{userId}")
    public ResponseEntity<Comment> addLike(@PathVariable String commentId, @PathVariable String userId) {
        Comment likedComment = commentService.addLike(commentId, userId);
        return ResponseEntity.ok(likedComment);
    }

    @PatchMapping("/remove-like/{commentId}/{userId}")
    public ResponseEntity<Comment> removeLike(@PathVariable String commentId, @PathVariable String userId) {
        Comment unlikedComment = commentService.removeLike(commentId, userId);
        return ResponseEntity.ok(unlikedComment);
    }

    @SneakyThrows
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable String id) {
        commentService.deleteComment(id);
        return GenerateResponse.success("Comment deleted", null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable String id) {
        Comment comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }
}