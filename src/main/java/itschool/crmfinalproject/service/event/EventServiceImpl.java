package itschool.crmfinalproject.service.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import itschool.crmfinalproject.entity.app.Contact;
import itschool.crmfinalproject.entity.app.event.Event;
import itschool.crmfinalproject.entity.app.event.EventCategory;
import itschool.crmfinalproject.enums.PaymentMethodEnum;
import itschool.crmfinalproject.enums.SubscriptionEnum;
import itschool.crmfinalproject.mapper.ContactMapper;
import itschool.crmfinalproject.mapper.EventMapper;
import itschool.crmfinalproject.model.contact.ContactBaseDTO;
import itschool.crmfinalproject.model.event.EventDTO;
import itschool.crmfinalproject.repository.ContactRepository;
import itschool.crmfinalproject.repository.EventRepository;
import itschool.crmfinalproject.repository.event.EventCategoryRepository;
import itschool.crmfinalproject.service.comment.CommentService;
import itschool.crmfinalproject.utility.GenerateResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ContactRepository contactRepository;
    private final EventCategoryRepository eventCategoryRepository;

    private final CommentService commentService;

    private final EventMapper eventMapper;
    private final ContactMapper contactMapper;

    @Override
    public List<EventDTO> findAllEvents() {
        return eventRepository.findAll().stream()
                .map(eventMapper::toEventDTO)
                .toList()
                .reversed();
    }

    @Override
    public List<EventDTO> findAllEventsForContact(Long contactId) {
        return eventRepository.findAll().stream()
                .filter(event -> event.getParticipantContacts().stream()
                        .anyMatch(contact -> contact.id().equals(contactId)))
                .map(eventMapper::toEventDTO)
                .toList();
    }

    public EventDTO findEventById(String eventId) {
        return eventRepository.findById(eventId)
                .map(eventMapper::toEventDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void createEvent(EventDTO eventDTO) {
        Event newEvent = eventMapper.toEvent(eventDTO);
        eventRepository.insert(newEvent);
    }

    @Override
    public EventDTO updateEvent(String eventId, EventDTO eventDTO) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isEmpty()) {
            throw new EntityNotFoundException("Event with ID " + eventId + " not found");
        }
        Event existingEvent = optionalEvent.get();

        eventMapper.updateEventFromDTO(eventDTO, existingEvent);

        Event updatedEvent = eventRepository.save(existingEvent);
        return eventMapper.toEventDTO(updatedEvent);
    }

    @Override
    public void deleteEvent(String eventId) throws JsonProcessingException {
        if (this.findEventById(eventId) != null) {
            commentService.findCommentsByEventId(eventId).forEach(comment -> {
                if (comment != null) { // Ensure the comment is not null before proceeding
                    commentService.deleteComment(comment.id());
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

        ContactBaseDTO newContact = contactMapper.toContactBaseDTO(contact);
        event.getParticipantContacts().add(newContact);
        eventRepository.save(event);

        return eventMapper.toEventDTO(event);
    }

    @Override
    public Map<String, List<String>> getAllEventTypes() {
        List<EventCategory> eventCategoryOptions = eventCategoryRepository.findAll();
        return eventCategoryOptions.stream()
                .collect(Collectors.toMap(
                                EventCategory::getType,
                                EventCategory::getOptions
                        )
                );
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