package itschool.crmfinalproject.service.contact;

import itschool.crmfinalproject.model.contact.ContactBaseDTO;
import itschool.crmfinalproject.model.contact.ContactDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ContactService {
    ResponseEntity<?> getContactById(Long contactId);

    ResponseEntity<Page<ContactDTO>> getAllContactsPaged(Pageable pageable);

    ResponseEntity<?> getAllContacts();

    ResponseEntity<?> updateContact(Long contactId, ContactBaseDTO contactDTO);

    ResponseEntity<?> deleteContactById(Long contactId);

    ResponseEntity<?> deleteAllContacts();

    void addContact(ContactBaseDTO contactDTO);
}