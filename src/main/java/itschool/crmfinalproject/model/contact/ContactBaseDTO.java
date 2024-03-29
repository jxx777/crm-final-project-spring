package itschool.crmfinalproject.model.contact;

public record ContactBaseDTO(
        Long id,
        String email,
        String firstName,
        String lastName,
        String position,
        String phoneNumber
) { }