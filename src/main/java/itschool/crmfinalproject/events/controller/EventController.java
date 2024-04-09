package itschool.crmfinalproject.events.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.events.model.EventDTO;
import itschool.crmfinalproject.events.service.EventCategoryService;
import itschool.crmfinalproject.events.service.EventService;
import itschool.crmfinalproject.common.utility.GenerateResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Tag(name = "Event Service", description = "Handles event-related functionalities, supporting event lifecycle management and queries.")
public class EventController {

    private final EventService eventService;
    private final EventCategoryService eventCategoryService;

    @Operation(summary = "Get all event types", description = "Retrieve a list of all event types.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved event types")
    @GetMapping("/types")
    public ResponseEntity<?> getAllEventTypes() {
        Map<String, List<String>> allEventTypes = eventService.getAllEventTypes();
        return ResponseEntity.ok(allEventTypes);
    }

    @Operation(summary = "Get all payment methods", description = "Retrieve a list of all payment methods.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved payment methods")
    @GetMapping("/payment-methods")
    public ResponseEntity<?> getAllPaymentMethods() {
        List<String> allPaymentMethods = eventService.getAllPaymentMethods();
        return ResponseEntity.ok(allPaymentMethods);
    }

    @Operation(summary = "Get all subscriptions types", description = "Retrieve a list of all subscription packages.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved subscription types")
    @GetMapping("/subscriptions")
    public ResponseEntity<?> getAllSubscriptionTypes() {
        List<String> allSubscriptionTypes = eventService.getAllSubscriptionTypes();
        return ResponseEntity.ok(allSubscriptionTypes);
    }

    @Operation(summary = "Get event options", description = "Retrieve a list of options based on the event category.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved event options")
    @GetMapping("/options/{type}")
    public ResponseEntity<List<String>> getEventOptions(@PathVariable String type) {
        List<String> eventCategoryOptions = eventCategoryService.getEventCategoryOptions(type);
        return ResponseEntity.ok(eventCategoryOptions);
    }

    @Operation(summary = "Create an event", description = "Create a new event.")
    @ApiResponse(responseCode = "200", description = "Successfully created an event")
    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody @Parameter(description = "Event data to create") EventDTO eventDto) throws JsonProcessingException {
        eventService.createEvent(eventDto);
        return GenerateResponse.created("Event created", null);
    }

    @Operation(summary = "Update event")
    @PatchMapping("/update/{eventId}")
    ResponseEntity<?> updateEvent(@PathVariable String eventId, @RequestBody EventDTO eventDTO) throws JsonProcessingException {
       EventDTO updatedEvent = eventService.updateEvent(eventId, eventDTO);
       return GenerateResponse.success("Event updated", updatedEvent);
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
}
