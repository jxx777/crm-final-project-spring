package itschool.crmfinalproject.controller;

import itschool.crmfinalproject.model.event.EventDTO;
import itschool.crmfinalproject.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    // This is a simplified example. You might want to fetch these from a service.
    private static final List<String> eventTypes = Arrays.asList("Call", "Purchase", "Meeting");
    private static final Map<String, List<String>> eventTypeOptions = Map.of("Call", Arrays.asList("Duration", "Caller ID", "Call Type", "Call Result"), "Purchase", Arrays.asList("Amount", "Purchase Date", "Items", "Payment Method"), "Meeting", Arrays.asList("Meeting Date", "Location", "Participants", "Agenda"));
    private final EventService eventService;

    @GetMapping("/types")
    public ResponseEntity<List<String>> getEventTypes() {
        return ResponseEntity.ok(eventTypes);
    }

    @GetMapping("/options/{type}")
    public ResponseEntity<List<String>> getEventOptions(@PathVariable String type) {
        List<String> options = eventTypeOptions.getOrDefault(type, List.of());
        return ResponseEntity.ok(options);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDto) {
        eventService.addEvent(eventDto);
        return ResponseEntity.ok("BaseEvent created successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEvents() {
        List<EventDTO> allEvents = eventService.findAllEvents();
        return ResponseEntity.ok(allEvents);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventById(@PathVariable String eventId) {
        Optional<EventDTO> event = eventService.findEventById(eventId);
        return ResponseEntity.ok(event.get());
    }

    @GetMapping("/contact/{contactId}")
    public ResponseEntity<?> findAllEventsWithContactDetails(@PathVariable Long contactId) {
        List<EventDTO> eventsForContact = eventService.findAllEventsWithContactDetails(contactId);
        return ResponseEntity.ok(eventsForContact);
    }
}