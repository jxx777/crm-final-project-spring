package itschool.crmfinalproject.events.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import itschool.crmfinalproject.events.model.EventDTO;
import itschool.crmfinalproject.events.model.NewEventDTO;


import java.util.List;
import java.util.Map;

/**
 * Service interface for managing event fields.
 */
public interface EventService {

    /**
     * Finds all events.
     *
     * @return a list of all event DTOs.
     */
    List<EventDTO> findAllEvents();

    /**
     * Finds events associated with a specific contact.
     *
     * @param contactId the ID of the contact.
     * @return a list of event DTOs for the given contact.
     */
    List<EventDTO> findAllEventsForContact(Long contactId);

    /**
     * Finds an event by its ID.
     *
     * @param eventId the ID of the event to find.
     * @return the event DTO if found.
     */
    EventDTO findEventById(String eventId);

    /**
     * Creates a new event.
     *
     * @param newEventDTO the event data transfer object.
     */
    void createEvent(NewEventDTO newEventDTO);

    /**
     * Updates an existing event.
     *
     * @param eventId  the ID of the event to update.
     * @param eventDTO the updated event data.
     * @return the updated event DTO.
     */
    EventDTO updateEvent(String eventId, EventDTO eventDTO);

    /**
     * Deletes an event.
     *
     * @param eventId the ID of the event to delete.
     */
    void deleteEvent(String eventId) throws JsonProcessingException;

    /**
     * Adds a contact to an event.
     *
     * @param eventId   the event ID.
     * @param contactId the contact ID to add.
     * @return the updated event DTO.
     */
    EventDTO addContactToEvent(String eventId, Long contactId);

    /**
     * Retrieves a map of all event types and their options.
     *
     * @return a map with event types as keys and their options as values.
     */
    Map<String, List<String>> getAllEventTypes();
    List<String> getAllPaymentMethods();
    List<String> getAllSubscriptionTypes();
    List<String> getAllEventCategoriesOptions();
}