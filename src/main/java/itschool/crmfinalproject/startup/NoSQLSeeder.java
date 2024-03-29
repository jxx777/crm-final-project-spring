package itschool.crmfinalproject.startup;

import com.github.javafaker.Faker;
import itschool.crmfinalproject.entity.app.Contact;
import itschool.crmfinalproject.entity.app.event.Comment;
import itschool.crmfinalproject.entity.app.event.Event;
import itschool.crmfinalproject.entity.app.event.EventTypeEnum;
import itschool.crmfinalproject.repository.CommentRepository;
import itschool.crmfinalproject.repository.ContactRepository;
import itschool.crmfinalproject.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class NoSQLSeeder implements CommandLineRunner {

    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final ContactRepository contactRepository;
    private final Faker faker;
    private final Random random = new Random();

    @Override
    public void run(String... args) {
        if (eventRepository.count() == 0) {
            List<String> contactIds = contactRepository.findAll().stream()
                    .map(Contact::getId)
                    .map(Object::toString)
                    .collect(Collectors.toList());

            IntStream.range(0, 50).forEach(i -> {  // Adjusted to create 50 events
                Event event = createAndSaveEvent(contactIds);
                Set<String> allCommentIds = createAndSaveCommentsForEvent(event);

                event.setCommentIds(allCommentIds);
                eventRepository.save(event);
            });
        }
    }

    private Event createAndSaveEvent(List<String> contactIds) {
        Event event = new Event();
        event.setTitle(faker.company().catchPhrase());
        event.setTime(LocalDateTime.now().minusDays(random.nextInt(1, 365)));
        event.setDescription(faker.lorem().paragraph());
        event.setEventType(EventTypeEnum.values()[random.nextInt(EventTypeEnum.values().length)]);
        event.setDetails(Map.of("location", faker.address().fullAddress(), "note", faker.lorem().sentence()));

        Set<String> eventContactIds = random.ints(1, contactIds.size())
                .limit(faker.number().numberBetween(1, 5))
                .mapToObj(contactIds::get)
                .collect(Collectors.toSet());
        event.setContactIds(eventContactIds);

        return eventRepository.save(event);
    }

    private Set<String> createAndSaveCommentsForEvent(Event event) {
        int numberOfComments = faker.number().numberBetween(1, 5);
        Set<String> commentIds = new HashSet<>();

        for (int i = 0; i < numberOfComments; i++) {
            Comment comment = createComment(event.getId(), null);
            comment = commentRepository.save(comment);
            commentIds.add(comment.getId());

            // If this is a top-level comment, potentially generate replies
            List<Comment> replies = createAndSaveRepliesForComment(event.getId(), comment.getId());
            commentIds.addAll(replies.stream().map(Comment::getId).collect(Collectors.toSet()));
        }

        return commentIds;
    }

    private List<Comment> createAndSaveRepliesForComment(String eventId, String parentId) {
        int numberOfReplies = faker.number().numberBetween(1, 5);
        List<Comment> replies = new ArrayList<>();

        for (int i = 0; i < numberOfReplies; i++) {
            Comment reply = createComment(eventId, parentId);
            reply = commentRepository.save(reply);
            replies.add(reply);
        }

        return replies;
    }

    private Comment createComment(String eventId, String parentId) {
        Comment comment = new Comment();
        comment.setParentId(parentId);
        comment.setEventId(eventId);
        comment.setAuthor(faker.name().fullName());
        comment.setText(faker.lorem().sentence());
        comment.setTimestamp(LocalDateTime.now().minusDays(random.nextInt(1, 30)));
        comment.setAttachments(generateAttachments());
        generateLikes(comment);
        return comment;
    }

    private List<String> generateAttachments() {
        return IntStream.range(0, random.nextInt(1, 4)).mapToObj(i -> faker.internet().avatar()).collect(Collectors.toList());
    }

    private void generateLikes(Comment comment) {
        IntStream.range(0, random.nextInt(1, 10)).forEach(i -> comment.likeComment(faker.idNumber().valid()));
    }
}