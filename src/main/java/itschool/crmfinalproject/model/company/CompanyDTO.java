package itschool.crmfinalproject.model.company;

import itschool.crmfinalproject.model.contact.ContactBaseDTO;
import java.util.List;

public record CompanyDTO(
        Long id,
        String name,
        Double evaluation,
        Double incomeFromCompany,
        List<ContactBaseDTO> contacts
) { }