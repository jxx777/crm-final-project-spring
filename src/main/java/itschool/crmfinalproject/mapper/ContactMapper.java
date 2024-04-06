package itschool.crmfinalproject.mapper;

import itschool.crmfinalproject.entity.app.Contact;
import itschool.crmfinalproject.model.contact.ContactBaseDTO;
import itschool.crmfinalproject.model.contact.ContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactDTO contactToContactDto(Contact contact);

    Contact contactDtoToContact(ContactDTO contactDTO);

    ContactBaseDTO contactToContactBaseDto(Contact contact);

    Contact contactBaseDtoToContact(ContactBaseDTO contactBaseDTO);

    void updateContactFromBaseContactDto(@MappingTarget Contact contact, ContactBaseDTO contactDTO);
}
