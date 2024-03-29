package itschool.crmfinalproject.repository;

import itschool.crmfinalproject.entity.app.event.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
}
