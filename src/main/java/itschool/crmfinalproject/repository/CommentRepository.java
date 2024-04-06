package itschool.crmfinalproject.repository;

import itschool.crmfinalproject.entity.app.event.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByAuthor(String author);

    List<Comment> findByEventId(String eventId);

    // Add method to delete a comment by its ID
//    void deleteById(String id);

    // Add method to find a comment by its ID
//    Optional<Comment> findById(String id);
}
