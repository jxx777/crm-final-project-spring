package itschool.crmfinalproject.mapper;

import itschool.crmfinalproject.entity.app.event.Event;
import itschool.crmfinalproject.model.event.EventDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event eventDtoToEvent(EventDTO eventDTO);

    EventDTO eventToEventDto(Event event);
}
