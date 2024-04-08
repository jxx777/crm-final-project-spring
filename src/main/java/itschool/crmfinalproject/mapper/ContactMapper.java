package itschool.crmfinalproject.mapper;

import itschool.crmfinalproject.entity.app.Contact;
import itschool.crmfinalproject.model.contact.ContactBaseDTO;
import itschool.crmfinalproject.model.contact.ContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactDTO toContactDTO(Contact contact);

    Contact toContact(ContactDTO contactDTO);

    ContactBaseDTO toContactBaseDTO(Contact contact);

    Contact baseToContact(ContactBaseDTO contactBaseDTO);

    void updateBaseContactDTO(@MappingTarget Contact contact, ContactBaseDTO contactDTO);
}
