package itschool.crmfinalproject.service.event;

import itschool.crmfinalproject.entity.app.event.Event;
import itschool.crmfinalproject.mapper.EventMapper;
import itschool.crmfinalproject.model.event.EventDTO;
import itschool.crmfinalproject.repository.EventRepository;
import itschool.crmfinalproject.service.contact.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final ContactService contactService;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventDTO> findAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::eventToEventDto)
                .toList();
    }

    @Override
    public List<EventDTO> findAllEventsWithContactDetails(Long contactId) {
        return eventRepository.findAll()
                .stream()
                .filter(event -> event.getContactIds().contains(contactId.toString()))
                .map(eventMapper::eventToEventDto)
                .toList();
    }

    @Override
    public void addEvent(EventDTO event) {
        Event newEvent = eventMapper.eventDtoToEvent(event);
        eventRepository.insert(newEvent);
    }

    public Optional<EventDTO> findEventById(String eventId) {
        return eventRepository.findById(eventId)
                .map(eventMapper::eventToEventDto);
    }
}