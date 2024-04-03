package itschool.crmfinalproject.model;

import java.util.List;

public record CommentDTO(
        String eventId,
        String author,
        String text,
        List<String> attachments
) {
}
