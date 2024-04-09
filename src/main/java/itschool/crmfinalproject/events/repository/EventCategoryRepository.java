package itschool.crmfinalproject.events.repository;

import itschool.crmfinalproject.events.document.EventCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventCategoryRepository extends MongoRepository<EventCategory, String> {
    EventCategory findByType(String type);

    void deleteByType(String type);
}