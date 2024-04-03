package itschool.crmfinalproject.controller;

import itschool.crmfinalproject.entity.app.event.EventTypeEnum;
import itschool.crmfinalproject.entity.app.event.PaymentMethodEnum;
import itschool.crmfinalproject.model.event.EventDTO;
import itschool.crmfinalproject.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private static final Map<String, List<String>> eventTypeOptions = Map.of("Call", Arrays.asList("Duration", "Caller ID", "Call Type", "Call Result"), "Purchase", Arrays.asList("Amount", "Purchase Date", "Items", "Payment Method"), "Meeting", Arrays.asList("Meeting Date", "Location", "Participants", "Agenda"));
    private final EventService eventService;

    @GetMapping("/types")
    public ResponseEntity<List<String>> getAllEventTypes() {
        List<String> eventTypes = Arrays.stream(EventTypeEnum.values())
                .map(Enum::toString)
                .toList();
        return ResponseEntity.ok(eventTypes);
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllPaymentMethods() {
        List<String> paymentMethods = Arrays.stream(PaymentMethodEnum.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/options/{type}")
    public ResponseEntity<List<String>> getEventOptions(@PathVariable String type) {
        List<String> options = eventTypeOptions.getOrDefault(type, List.of());
        return ResponseEntity.ok(options);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDto) {
        eventService.addEvent(eventDto);
        return ResponseEntity.ok(eventDto);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEvents() {
        List<EventDTO> allEvents = eventService.findAllEvents();
        return ResponseEntity.ok(allEvents);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventById(@PathVariable String eventId) {
        Optional<EventDTO> eventOptional = Optional.ofNullable(eventService.findEventById(eventId));
        return ResponseEntity.ok(eventOptional.get());
    }

    @GetMapping("/contact/{contactId}")
    public ResponseEntity<?> findAllEventsForContact(@PathVariable Long contactId) {
        List<EventDTO> eventsForContact = eventService.findAllEventsForContact(contactId);
        return ResponseEntity.ok(eventsForContact);
    }
}