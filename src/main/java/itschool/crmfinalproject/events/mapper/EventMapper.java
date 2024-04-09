package itschool.crmfinalproject.events.mapper;

import itschool.crmfinalproject.events.document.Event;
import itschool.crmfinalproject.events.model.EventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEvent(EventDTO eventDTO);

    EventDTO toEventDTO(Event event);

    void updateEventFromDTO(EventDTO eventDTO, @MappingTarget Event event);
}