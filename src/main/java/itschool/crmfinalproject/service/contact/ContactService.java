package itschool.crmfinalproject.service.contact;

import itschool.crmfinalproject.model.contact.ContactBaseDTO;
import itschool.crmfinalproject.model.contact.ContactDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContactService {
    ResponseEntity<?> getContactById(Long contactId);

    ResponseEntity<Page<ContactDTO>> getAllContactsPaged(Pageable pageable);

    List<ContactDTO> getAllContacts();

    ResponseEntity<?> updateContact(Long contactId, ContactBaseDTO contactDTO);

    void deleteContactById(Long contactId);

    void deleteAllContacts();

    void addContact(ContactBaseDTO contactDTO);
}