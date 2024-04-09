package itschool.crmfinalproject.events.repository;

import itschool.crmfinalproject.events.document.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> { }
