package itschool.crmfinalproject.service.event;

import itschool.crmfinalproject.model.event.EventDTO;

import java.util.List;

public interface EventService {
    List<EventDTO> findAllEvents();
    List<EventDTO> findAllEventsForContact(Long contactId);
    void addEvent(EventDTO event);
    EventDTO findEventById(String eventId);
}