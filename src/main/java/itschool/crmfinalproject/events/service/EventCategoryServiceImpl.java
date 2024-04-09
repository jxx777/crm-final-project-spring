package itschool.crmfinalproject.events.service;

import itschool.crmfinalproject.events.document.EventCategory;
import itschool.crmfinalproject.events.repository.EventCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventCategoryServiceImpl implements EventCategoryService {

    private final EventCategoryRepository eventCategoryRepository;

    @Override
    public void addEventCategory(EventCategory eventCategory) {
        eventCategoryRepository.save(eventCategory);
    }

    @Override
    public List<String> getEventCategoryOptions(String type) {
        EventCategory category = eventCategoryRepository.findByType(type);
        return category != null ? category.getOptions() : Collections.emptyList();
    }

    @Override
    public EventCategory updateEventTypeOptions(String type, List<String> options) {
        EventCategory eventCategory = eventCategoryRepository.findByType(type);
        if (eventCategory == null) {
            return null;
        } else {
            eventCategory.setOptions(options);
            return eventCategoryRepository.save(eventCategory);
        }
    }

    @Override
    public void deleteEventTypeOptions(String type) {
        eventCategoryRepository.deleteByType(type);
    }

    @Override
    public List<EventCategory> getAllEventCategories() {
        return eventCategoryRepository.findAll();
    }
}