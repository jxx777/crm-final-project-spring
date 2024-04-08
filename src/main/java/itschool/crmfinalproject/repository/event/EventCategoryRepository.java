package itschool.crmfinalproject.repository.event;

import itschool.crmfinalproject.entity.app.event.EventCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventCategoryRepository extends MongoRepository<EventCategory, String> {
    EventCategory findByType(String type);

    void deleteByType(String type);
}