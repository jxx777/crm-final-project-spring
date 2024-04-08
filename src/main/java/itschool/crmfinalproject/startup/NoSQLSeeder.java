package itschool.crmfinalproject.startup;

import com.github.javafaker.Faker;
import itschool.crmfinalproject.entity.app.event.Comment;
import itschool.crmfinalproject.entity.app.event.Event;
import itschool.crmfinalproject.entity.app.event.EventCategory;
import itschool.crmfinalproject.repository.CommentRepository;
import itschool.crmfinalproject.repository.EventRepository;
import itschool.crmfinalproject.repository.event.EventCategoryRepository;
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
    private final EventCategoryRepository eventCategoryRepository;
    private final CommentRepository commentRepository;
    private final Faker faker = new Faker();
    private final Random random = new Random();

    @Override
    public void run(String... args) {
        if (eventCategoryRepository.count() == 0) {
            seedEventTypeOptions();
        }

        if (eventRepository.count() == 0) {
            List<Event> events = IntStream.range(0, 50)
                    .mapToObj(i -> createEvent())
                    .toList();
            eventRepository.saveAll(events);
            events.forEach(this::createAndSaveCommentsForEvent);
        }
    }

    private void seedEventTypeOptions() {
        List<EventCategory> eventCategoryOptionsList = List.of(
                new EventCategory("call", Arrays.asList("Duration", "Caller ID", "Call Type", "Call Result")),
                new EventCategory("acquisition", Arrays.asList("Amount", "Purchase Date", "Subscription", "Payment Method")),
                new EventCategory("meeting", Arrays.asList("Meeting Date", "Location", "Participants", "Agenda")),
                new EventCategory("cancellation", Arrays.asList("Reason", "Feedback", "Unsubscribe")),
                new EventCategory("webinar", Arrays.asList("Webinar Date", "Topic", "Host", "Attendees")),
                new EventCategory("product launch", Arrays.asList("Launch Date", "Product Name", "Features", "Target Audience")),
                new EventCategory("networking", Arrays.asList("Event Date", "Venue", "Attendees", "Purpose"))
        );
        eventCategoryRepository.saveAll(eventCategoryOptionsList);
    }

    private Event createEvent() {
        String eventCategory = getRandomEventType();
        Map<String, Object> fieldDetails = generateEventDetails(eventCategory);

        Event event = new Event();
        event.setTitle(faker.company().catchPhrase());
        event.setTime(LocalDateTime.now().minusDays(random.nextInt(365)));
        event.setDescription(faker.lorem().paragraph());
        event.setEventCategory(eventCategory);
        event.setFieldDetails(fieldDetails);
        return event;
    }

    private String getRandomEventType() {
        List<String> eventCategories = List.of("call", "acquisition", "meeting", "cancellation");
        return eventCategories.get(random.nextInt(eventCategories.size()));
    }

    private Map<String, Object> generateEventDetails(String eventCategory) {
        Map<String, Object> fieldDetails = new HashMap<>();
        // This should ideally be adjusted to dynamically generate fieldDetails based on the eventCategory
        fieldDetails.put("Location", faker.address().fullAddress());
        fieldDetails.put("Note", faker.lorem().sentence());
        return fieldDetails;
    }

    private void createAndSaveCommentsForEvent(Event event) {
        // Use a skewed distribution: most events have a moderate number of comments, but a few have many.
        int numberOfComments = (random.nextBoolean()) ? random.nextInt(1, 5) : random.nextInt(5, 15);
        List<Comment> comments = IntStream.range(0, numberOfComments)
                .mapToObj(i -> createComment(event.getId()))
                .collect(Collectors.toList());
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
                .toList();
    }
}
