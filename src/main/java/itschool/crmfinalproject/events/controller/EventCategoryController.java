package itschool.crmfinalproject.events.controller;

import itschool.crmfinalproject.events.document.EventCategory;
import itschool.crmfinalproject.events.service.EventCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event-categories")
@RequiredArgsConstructor
public class EventCategoryController {

    private final EventCategoryService eventCategoryService;

    @PostMapping
    public ResponseEntity<EventCategory> addEventCategory(@RequestBody EventCategory eventCategory) {
        eventCategoryService.addEventCategory(eventCategory);
        return ResponseEntity.ok(eventCategory);
    }

    @PutMapping("/{type}")
    public ResponseEntity<EventCategory> updateEventTypeOptions(@PathVariable String type, @RequestBody List<String> options) {
        EventCategory updatedEventCategory = eventCategoryService.updateEventTypeOptions(type, options);
        if (updatedEventCategory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedEventCategory);
    }

    @DeleteMapping("/{type}")
    public ResponseEntity<?> deleteEventTypeOptions(@PathVariable String type) {
        eventCategoryService.deleteEventTypeOptions(type);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<EventCategory>> getAllEventCategories() {
        List<EventCategory> categories = eventCategoryService.getAllEventCategories();
        return ResponseEntity.ok(categories);
    }
}
