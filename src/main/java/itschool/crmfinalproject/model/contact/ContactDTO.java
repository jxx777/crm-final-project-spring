package itschool.crmfinalproject.model.contact;

import itschool.crmfinalproject.entity.app.Address;
import itschool.crmfinalproject.model.company.CompanyBaseDTO;

import java.time.LocalDateTime;
import java.util.Set;

public record ContactDTO(
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy,
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        CompanyBaseDTO company,
        String position,
        Address address,
        String description,
        Set<String> tags
) { }