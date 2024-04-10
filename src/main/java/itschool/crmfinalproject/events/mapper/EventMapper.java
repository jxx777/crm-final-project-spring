package itschool.crmfinalproject.events.mapper;

import itschool.crmfinalproject.events.document.Event;
import itschool.crmfinalproject.events.model.EventDTO;
import itschool.crmfinalproject.events.model.NewEventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEvent(EventDTO eventDTO);

    Event newToEvent(NewEventDTO newEventDTO);

    EventDTO toEventDTO(Event event);

    // Updates an existing Event entity with values from EventDTO, potentially including category updates
    void updateEventFromDTO(EventDTO eventDTO, @MappingTarget Event event);
}