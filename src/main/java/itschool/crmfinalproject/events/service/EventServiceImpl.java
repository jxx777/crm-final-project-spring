package itschool.crmfinalproject.events.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import itschool.crmfinalproject.contacts.entity.Contact;
import itschool.crmfinalproject.events.document.Event;
import itschool.crmfinalproject.events.document.EventCategory;
import itschool.crmfinalproject.events.enums.PaymentMethodEnum;
import itschool.crmfinalproject.events.enums.SubscriptionEnum;
import itschool.crmfinalproject.contacts.mapper.ContactMapper;
import itschool.crmfinalproject.events.mapper.EventMapper;
import itschool.crmfinalproject.contacts.model.ContactBaseDTO;
import itschool.crmfinalproject.events.model.EventCategoryDTO;
import itschool.crmfinalproject.events.model.EventDTO;
import itschool.crmfinalproject.contacts.repository.ContactRepository;
import itschool.crmfinalproject.events.model.NewEventDTO;
import itschool.crmfinalproject.events.repository.EventRepository;
import itschool.crmfinalproject.events.repository.EventCategoryRepository;
import itschool.crmfinalproject.comments.service.CommentService;
import itschool.crmfinalproject.common.utility.GenerateResponse;
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
    public void createEvent(NewEventDTO newEventDTO) {
        Event newEvent = eventMapper.newToEvent(newEventDTO);
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

    @Override
    public List<String> getAllEventCategoriesOptions() {
        return eventCategoryRepository.findAll()
                .stream()
                .map(EventCategory::getType)
                .collect(Collectors.toList());
    }
}