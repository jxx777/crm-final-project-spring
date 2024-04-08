package itschool.crmfinalproject.service.event;

import itschool.crmfinalproject.entity.app.event.EventCategory;
import java.util.List;

public interface EventCategoryService {

    void addEventCategory(EventCategory eventCategory);

    List<String> getEventCategoryOptions(String type);

    EventCategory updateEventTypeOptions(String type, List<String> options);

    void deleteEventTypeOptions(String type);

    List<EventCategory> getAllEventCategories();

}