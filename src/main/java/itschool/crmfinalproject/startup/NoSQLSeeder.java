package itschool.crmfinalproject.startup;

import com.github.javafaker.Faker;
import itschool.crmfinalproject.entity.app.event.Comment;
import itschool.crmfinalproject.entity.app.event.Event;
import itschool.crmfinalproject.entity.app.event.EventTypeEnum;
import itschool.crmfinalproject.repository.CommentRepository;
import itschool.crmfinalproject.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class NoSQLSeeder implements CommandLineRunner {

    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final Faker faker = new Faker();
    private final Random random = new Random();

    @Override
    public void run(String... args) {
        if (eventRepository.count() == 0) {
            List<Event> events = IntStream.range(0, 50)
                    .mapToObj(i -> createEvent())
                    .collect(Collectors.toList());
            eventRepository.saveAll(events);
            events.forEach(this::createAndSaveCommentsForEvent);
        }
    }

    private Event createEvent() {
        EventTypeEnum eventType = EventTypeEnum.values()[random.nextInt(EventTypeEnum.values().length)];
        Map<String, Object> details = generateEventDetails(eventType);

        Event event = new Event();
        event.setTitle(faker.company().catchPhrase());
        event.setTime(LocalDateTime.now().minusDays(random.nextInt(365)));
        event.setDescription(faker.lorem().paragraph());
        event.setEventType(eventType);
        event.setDetails(details);
        return event;
    }

    private Map<String, Object> generateEventDetails(EventTypeEnum eventType) {
        Map<String, Object> details = new HashMap<>();
        details.put("Location", faker.address().fullAddress());
        details.put("Note", faker.lorem().sentence());

        if (eventType == EventTypeEnum.CALL) {
            details.put("Duration", faker.number().digit() + " mins");
            details.put("Caller ID", faker.phoneNumber().phoneNumber());
        } else if (eventType == EventTypeEnum.MEETING) {
            details.put("Meeting Date", faker.date().future(30, TimeUnit.DAYS).toString());
            details.put("Participants", faker.number().numberBetween(1, 10));
            details.put("Agenda", faker.lorem().sentence());
        }
        // Add additional details for other event types

        return details;
    }

    private void createAndSaveCommentsForEvent(Event event) {
        int numberOfComments = random.nextInt(1, 5);
        List<Comment> comments = IntStream.range(0, numberOfComments)
                .mapToObj(i -> createComment(event.getId()))
                .toList();
        commentRepository.saveAll(comments);
    }

    private Comment createComment(String eventId) {
        Comment comment = new Comment();
        comment.setEventId(eventId);
        comment.setAuthor(faker.name().fullName());
        comment.setText(faker.lorem().sentence());
        comment.setTimestamp(LocalDateTime.now().minusDays(random.nextInt(30)));
        comment.setAttachments(generateAttachments());
        return comment;
    }

    private List<String> generateAttachments() {
        return IntStream.range(0, random.nextInt(1, 4))
                .mapToObj(i -> faker.file().fileName())
                .collect(Collectors.toList());
    }
}