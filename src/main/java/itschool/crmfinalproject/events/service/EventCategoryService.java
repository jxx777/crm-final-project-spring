package itschool.crmfinalproject.events.service;

import itschool.crmfinalproject.events.document.EventCategory;

import java.util.List;

public interface EventCategoryService {

    void addEventCategory(EventCategory eventCategory);

    List<String> getEventCategoryOptions(String type);

    EventCategory updateEventTypeOptions(String type, List<String> options);

    void deleteEventTypeOptions(String type);

    List<EventCategory> getEventCategories();
}