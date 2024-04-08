package itschool.crmfinalproject.mapper;

import itschool.crmfinalproject.entity.app.event.Event;
import itschool.crmfinalproject.model.event.EventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEvent(EventDTO eventDTO);

    EventDTO toEventDTO(Event event);

    void updateEventFromDTO(EventDTO eventDTO, @MappingTarget Event event);
}