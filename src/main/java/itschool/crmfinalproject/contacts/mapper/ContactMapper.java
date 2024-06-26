package itschool.crmfinalproject.contacts.mapper;

import itschool.crmfinalproject.contacts.entity.Contact;
import itschool.crmfinalproject.contacts.model.ContactBaseDTO;
import itschool.crmfinalproject.contacts.model.ContactDTO;
import itschool.crmfinalproject.contacts.model.NewContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactDTO toContactDTO(Contact contact);

    Contact toContact(ContactDTO contactDTO);

    ContactBaseDTO toContactBaseDTO(Contact contact);

    Contact baseToContact(ContactBaseDTO contactBaseDTO);

    Contact newToContact(NewContactDTO newContactDTO);

    NewContactDTO contactToNewContactDTO(Contact contact);

    void updateBaseContactDTO(@MappingTarget Contact contact, ContactBaseDTO contactDTO);
}
