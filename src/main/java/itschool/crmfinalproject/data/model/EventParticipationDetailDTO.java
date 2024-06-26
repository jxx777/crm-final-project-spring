package itschool.crmfinalproject.data.model;

import java.time.LocalDateTime;

/**
 * Detailed information about event participation, including event details and associated income.
 */
public record EventParticipationDetailDTO(
        String eventId,
        String title,
        LocalDateTime time,
        int participantCount,
        double income
) {}
