package itschool.crmfinalproject.model.event;

import itschool.crmfinalproject.entity.app.event.EventTypeEnum;
import itschool.crmfinalproject.model.contact.ContactBaseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record EventDTO(
        String id,
        String title,
        LocalDateTime time,
        String description,
        EventTypeEnum eventType,
        Map<String, Object> details,
        List<String> commentIds,
        Set<ContactBaseDTO> contacts
) {
}