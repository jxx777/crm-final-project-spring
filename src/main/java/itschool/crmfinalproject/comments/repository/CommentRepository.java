package itschool.crmfinalproject.comments.repository;

import itschool.crmfinalproject.comments.document.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByAuthor(String author);

    List<Comment> findByEventId(String eventId);
}
