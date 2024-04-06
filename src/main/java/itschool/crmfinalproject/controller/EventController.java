package itschool.crmfinalproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.model.event.EventDTO;
import itschool.crmfinalproject.service.event.EventService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Tag(name = "Event Service", description = "Handles event-related functionalities, supporting event lifecycle management and queries.")
public class EventController {
    private final EventService eventService;

    @Operation(summary = "Get all event types", description = "Retrieve a list of all event types.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved event types")
    @GetMapping("/types")
    public ResponseEntity<List<String>> getAllEventTypes() {
        return ResponseEntity.ok(eventService.getAllEventTypes());
    }

    @Operation(summary = "Get all payment methods", description = "Retrieve a list of all payment methods.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved payment methods")
    @GetMapping("/payment-methods")
    public ResponseEntity<List<String>> getAllPaymentMethods() {
        return ResponseEntity.ok(eventService.getAllPaymentMethods());
    }

    @Operation(summary = "Get all subscriptions types", description = "Retrieve a list of all subscription packages.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved subscription types")
    @GetMapping("/subscriptions")
    public ResponseEntity<List<String>> getAllSubscriptionTypes() {
        return ResponseEntity.ok(eventService.getAllSubscriptionTypes());
    }

    @Operation(summary = "Get event options", description = "Retrieve a list of options based on the event type.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved event options")
    @GetMapping("/options/{type}")
    public ResponseEntity<List<String>> getEventOptions(@PathVariable @Parameter(description = "Event type to retrieve options for") String type) {
        List<String> eventTypeOptions = eventService.eventTypeOptions(type);
        return ResponseEntity.ok(eventTypeOptions);
    }

    @Operation(summary = "Create an event", description = "Create a new event.")
    @ApiResponse(responseCode = "200", description = "Successfully created an event")
    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody @Parameter(description = "Event data to create") EventDTO eventDto) {
        eventService.addEvent(eventDto);
        return ResponseEntity.ok(eventDto);
    }

    @Operation(summary = "Get all events", description = "Retrieve a list of all events.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all events")
    @GetMapping("/all")
    public ResponseEntity<?> getAllEvents() {
        List<EventDTO> allEvents = eventService.findAllEvents();
        return ResponseEntity.ok(allEvents);
    }

    @Operation(summary = "Get event by ID", description = "Retrieve an event by its ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the event")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventById(@PathVariable @Parameter(description = "ID of the event to retrieve") String eventId) {
        EventDTO eventOptional = Optional.of(eventService.findEventById(eventId)).get();
        return ResponseEntity.ok(eventOptional);
    }

    @Operation(summary = "Get events for a contact", description = "Retrieve all events associated with a contact ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved events for the contact")
    @GetMapping("/contact/{contactId}")
    public ResponseEntity<?> findAllEventsForContact(@PathVariable @Parameter(description = "Contact ID to retrieve events for") Long contactId) {
        List<EventDTO> allEventsForContact = eventService.findAllEventsForContact(contactId);
        return ResponseEntity.ok(allEventsForContact);
    }

    @Operation(summary = "Add contact to event", description = "Add a new contact to the event.")
    @ApiResponse(responseCode = "200", description = "Successfully added contact to the event")
    @PatchMapping("/{eventId}/add-contact/{contactId}")
    public ResponseEntity<EventDTO> addContactToEvent(
            @PathVariable @Parameter(description = "Event ID to add contact to") String eventId,
            @PathVariable @Parameter(description = "Contact ID to add to the event") Long contactId
    ) {
        EventDTO eventWithAddedContact = eventService.addContactToEvent(eventId, contactId);
        return ResponseEntity.ok(eventWithAddedContact);
    }

    @SneakyThrows
    @Operation(summary = "Delete event", description = "Remove an event from the database.")
    @ApiResponse(responseCode = "200", description = "Successfully deleted the event")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable @Parameter(description = "Event ID to delete") String eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok().body("Event deleted");
    }
}