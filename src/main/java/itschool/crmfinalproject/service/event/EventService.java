package itschool.crmfinalproject.service.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import itschool.crmfinalproject.entity.app.event.EventTypeEnum;
import itschool.crmfinalproject.entity.app.event.SubscriptionEnum;
import itschool.crmfinalproject.entity.app.event.PaymentMethodEnum;
import itschool.crmfinalproject.model.event.EventDTO;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

/**
 * Service interface for event-related operations.
 */
public interface EventService {

    /**
     * Find all events.
     *
     * @return a list of all events.
     */
    List<EventDTO> findAllEvents();

    /**
     * Find all events for a specific contact.
     *
     * @param contactId the ID of the contact.
     * @return a list of {@link EventDTO}s associated with the contact.
     */
    List<EventDTO> findAllEventsForContact(Long contactId);

    /**
     * Add a new event.
     *
     * @param event the event to add.
     */
    void addEvent(EventDTO event);

    /**
     * Adds a contact to an event.
     *
     * @param eventId The ID of the event to which the contact will be added.
     * @param contactId The ID of the contact to add to the event.
     * @return The updated event with the new contact added.
     * @throws EntityNotFoundException If the event or contact does not exist.
     */
    EventDTO addContactToEvent(String eventId, Long contactId);

    /**
     * Find an event by its ID.
     *
     * @param eventId the ID of the event.
     * @return the found event.
     */
    EventDTO findEventById(String eventId);

    /**
     * Get event options based on the event type.
     *
     * @param type the type of the event.
     * @return a list of options for the event type.
     */
    List<String> eventTypeOptions(String type);

    /**
     * Removes an event from the database.
     *
     * @param eventId UUID of the event.
     */
    void deleteEvent(String eventId) throws JsonProcessingException;

    /**
     * Get event type.
     *
     * @return a list of {@link EventTypeEnum}, should the event be of 'ACQUISITION'.
     */
    List<String> getAllEventTypes();

    /**
     * Get payment method.
     *
     * @return a list of {@link PaymentMethodEnum}, should the event be of 'ACQUISITION'.
     */
    List<String> getAllPaymentMethods();

    /**
     * Get subscription package.
     *
     * @return a list of {@link SubscriptionEnum}, should the event be of 'ACQUISITION'.
     */
    List<String> getAllSubscriptionTypes();
}