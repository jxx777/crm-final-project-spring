package itschool.crmfinalproject.events.document;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "eventCategoryOptions")
@RequiredArgsConstructor
public class EventCategory {
    @Id
    private String id;

    private String type;
    private List<String> options;

    public EventCategory(String type, List<String> options) {
        this.type = type;
        this.options = new ArrayList<>(options);
    }
}
