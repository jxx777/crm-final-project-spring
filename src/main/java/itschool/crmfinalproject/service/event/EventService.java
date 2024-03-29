package itschool.crmfinalproject.service.event;

import itschool.crmfinalproject.model.event.EventDTO;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<EventDTO> findAllEvents();
    List<EventDTO> findAllEventsWithContactDetails(Long contactId);
    void addEvent(EventDTO event);
    Optional<EventDTO> findEventById(String eventId);
}