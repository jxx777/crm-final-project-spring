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
public class UnstructuredDataSeeder implements CommandLineRunner {

    private final EventRepository eventRepository;
    private final EventCategoryRepository eventCategoryRepository;
    private final CommentRepository commentRepository;
    private final Faker faker = new Faker();
    private final Random random = new Random();

    @Override
    public void run(String... args) {
        if (eventCategoryRepository.count() == 0) seedEventTypeOptions();

        if (eventRepository.count() == 0) {
            List<Event> events = IntStream.range(225, 475).mapToObj(i -> createEvent()).collect(Collectors.toList());
            eventRepository.saveAll(events);
            events.forEach(this::createAndSaveCommentsForEvent);
        }
    }

    // Creates diverse event categories with specific details
    private void seedEventTypeOptions() {
        List<EventCategory> eventCategoryOptionsList = Arrays.asList(
                // Define various event categories with relevant details
                new EventCategory("call", Arrays.asList("Duration", "Caller ID", "Call Type", "Call Result")), new EventCategory("acquisition", Arrays.asList("Amount", "Purchase Date", "Subscription", "Payment Method")), new EventCategory("meeting", Arrays.asList("Meeting Date", "Location", "Participants", "Agenda")), new EventCategory("cancellation", Arrays.asList("Reason", "Feedback", "Unsubscribe")), new EventCategory("webinar", Arrays.asList("Webinar Date", "Topic", "Host", "Attendees")), new EventCategory("product launch", Arrays.asList("Launch Date", "Product Name", "Features", "Target Audience")), new EventCategory("networking", Arrays.asList("Event Date", "Venue", "Attendees", "Purpose")));
        eventCategoryRepository.saveAll(eventCategoryOptionsList);
    }

    // Generates events with randomized data
    private Event createEvent() {
        String eventCategory = getRandomEventType();
        Event event = new Event();
        event.setTitle(faker.company().catchPhrase());
        event.setTime(LocalDateTime.now().minusDays(random.nextInt(365)));
        event.setDescription(faker.lorem().paragraph());
        event.setEventCategory(eventCategory);
        event.setFieldDetails(generateEventDetails(eventCategory));
        if ((Objects.equals(event.getEventCategory(), "acquisition"))) {
            event.setIncome(getVariedBound() * 100);
        }
        return event;
    }

    // Randomly selects an event type
    private String getRandomEventType() {
        List<String> eventCategories = Arrays.asList("call", "acquisition", "meeting", "cancellation", "webinar", "product launch", "networking");
        return eventCategories.get(random.nextInt(eventCategories.size()));
    }

    // Generates details based on the event category
    private Map<String, Object> generateEventDetails(String eventCategory) {
        Map<String, Object> fieldDetails = new HashMap<>();
        fieldDetails.put("Location", faker.address().fullAddress());
        fieldDetails.put("Note", faker.lorem().sentence());
        // Add more logic to customize details based on the event category
        return fieldDetails;
    }

    private void createAndSaveCommentsForEvent(Event event) {
        List<Comment> comments = new ArrayList<>();

        // Create top-level comments
        int numberOfComments = getVariedBound();
        for (int i = 0; i < numberOfComments; i++) {
            Comment comment = createComment(event.getId(), null); // Create a parent comment
            comments.add(comment);

            // Create replies for some comments
            if (random.nextBoolean()) {
                int numberOfReplies = random.nextInt(1, 4);
                for (int j = 0; j < numberOfReplies; j++) {
                    Comment reply = createComment(event.getId(), comment.getId()); // Create a reply
                    comments.add(reply);
                    comment.addReply(reply); // Link the reply to its parent
                }
            }
        }

        commentRepository.saveAll(comments); // Save all comments and replies
    }

    private int getVariedBound() {
        return random.nextBoolean()
            ? random.nextInt(3, 5)
            : random.nextInt(5, 15);
    }
    private Comment createComment(String eventId, String parentId) {
        Comment comment = new Comment();
        comment.setEventId(eventId);
        comment.setAuthor(faker.name().fullName());
        comment.setText(faker.lorem().sentence());
        comment.setTimestamp(LocalDateTime.now().minusDays(random.nextInt(30)));
        comment.setAttachments(generateAttachments());
        comment.setParentId(parentId); // Set the parent ID for replies
        return comment;
    }

    // Generates a list of random attachments for a comment
    private List<String> generateAttachments() {
        return IntStream.range(0, random.nextInt(1, 4)).mapToObj(i -> faker.file().fileName()).toList();
    }
}
