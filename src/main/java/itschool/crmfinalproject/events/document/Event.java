package itschool.crmfinalproject.events.document;

import itschool.crmfinalproject.contacts.model.ContactBaseDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Document(collection = "events")
@Getter
@Setter
public class Event {
    @Id
    private String id;

    private String title;

    private LocalDateTime time;

    private String description;

    private String eventCategory;

    private double income;

    private Map<String, Object> fieldDetails = new HashMap<>();

    private Set<String> commentIds = new HashSet<>();

    private Set<ContactBaseDTO> participantContacts = new HashSet<>();
}