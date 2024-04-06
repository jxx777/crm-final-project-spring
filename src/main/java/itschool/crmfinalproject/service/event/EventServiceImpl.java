package itschool.crmfinalproject.service.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import itschool.crmfinalproject.entity.app.Contact;
import itschool.crmfinalproject.entity.app.event.Event;
import itschool.crmfinalproject.entity.app.event.EventTypeEnum;
import itschool.crmfinalproject.entity.app.event.PaymentMethodEnum;
import itschool.crmfinalproject.entity.app.event.SubscriptionEnum;
import itschool.crmfinalproject.mapper.ContactMapper;
import itschool.crmfinalproject.mapper.EventMapper;
import itschool.crmfinalproject.model.contact.ContactBaseDTO;
import itschool.crmfinalproject.model.event.EventDTO;
import itschool.crmfinalproject.repository.ContactRepository;
import itschool.crmfinalproject.repository.EventRepository;
import itschool.crmfinalproject.service.comment.CommentService;
import itschool.crmfinalproject.utility.GenerateResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ContactRepository contactRepository;

    private final CommentService commentService;

    private final EventMapper eventMapper;
    private final ContactMapper contactMapper;

    @Override
    public List<EventDTO> findAllEvents() {
        return eventRepository.findAll().stream()
                .map(eventMapper::eventToEventDto)
                .toList()
                .reversed();
    }

    @Override
    public List<EventDTO> findAllEventsForContact(Long contactId) {
        return eventRepository.findAll().stream()
                .filter(event -> event.getContacts().stream()
                        .anyMatch(contact -> contact.id().equals(contactId)))
                .map(eventMapper::eventToEventDto)
                .toList();
    }

    @Override
    public void addEvent(EventDTO event) {
        Event newEvent = eventMapper.eventDtoToEvent(event);
        eventRepository.insert(newEvent);
    }

    public EventDTO findEventById(String eventId) {
        return eventRepository.findById(eventId)
                .map(eventMapper::eventToEventDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<String> eventTypeOptions(String type) {
        Map<String, List<String>> eventTypeOptions = new HashMap<>();
        eventTypeOptions.put("call", Arrays.asList("Duration", "Caller ID", "Call Type", "Call Result"));
        eventTypeOptions.put("acquisition", Arrays.asList("Amount", "Purchase Date", "Subscription", "Payment Method"));
        eventTypeOptions.put("meeting", Arrays.asList("Meeting Date", "Location", "Participants", "Agenda"));
        eventTypeOptions.put("cancellation", Arrays.asList("Reason", "Feedback", "Unsubscribe"));
        // Extend event fields based on above format...

        return eventTypeOptions.getOrDefault(type.toLowerCase(), List.of());
    }

    @Override
    public void deleteEvent(String eventId) throws JsonProcessingException {
        if (this.findEventById(eventId) != null) {
            commentService.findCommentsByEventId(eventId).forEach(comment -> {
                if (comment != null) { // Ensure the comment is not null before proceeding
                    commentService.deleteComment(comment.getId());
                }
            });
            eventRepository.deleteById(eventId);
            GenerateResponse.success("Event Successfully deleted", null);
        } else {
            GenerateResponse.notFound("Event Not Found", null);
        }
    }

    @Override
    public EventDTO addContactToEvent(String eventID, Long contactId) {
        Event event = eventRepository.findById(eventID).orElseThrow(() -> new EntityNotFoundException("Event not found with ID: " + eventID));

        Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new EntityNotFoundException("Contact not found with ID: " + contactId));

        ContactBaseDTO newContact = contactMapper.contactToContactBaseDto(contact);
        event.getContacts().add(newContact);
        eventRepository.save(event);

        return eventMapper.eventToEventDto(event);
    }

    @Override
    public List<String> getAllEventTypes() {
        return Arrays.stream(EventTypeEnum.values())
                .map(Enum::toString)
                .toList();
    }

    @Override
    public List<String> getAllPaymentMethods() {
        return Arrays.stream(PaymentMethodEnum.values())
                .map(Enum::toString)
                .toList();
    }

    @Override
    public List<String> getAllSubscriptionTypes() {
        return Arrays.stream(SubscriptionEnum.values())
                .map(Enum::toString)
                .toList();
    }
}