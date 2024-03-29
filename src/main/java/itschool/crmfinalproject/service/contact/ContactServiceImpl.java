package itschool.crmfinalproject.service.contact;

import itschool.crmfinalproject.entity.app.Contact;
import itschool.crmfinalproject.exceptions.ContactNotFoundException;
import itschool.crmfinalproject.mapper.ContactMapper;
import itschool.crmfinalproject.model.contact.ContactBaseDTO;
import itschool.crmfinalproject.model.contact.ContactDTO;
import itschool.crmfinalproject.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Override
    public ResponseEntity<?> getContactById(Long contactId) {
        return contactRepository.findById(contactId)
                .map(contactMapper::contactToContactDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ContactNotFoundException("Contact with the provided ID does not exist")
        );
    }

    @Override
    public ResponseEntity<?> deleteContactById(Long contactId) {
        if (contactRepository.findById(contactId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        contactRepository.deleteById(contactId);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Contact with ID " + contactId + " was deleted.");
        response.put("contactId", contactId);
        return ResponseEntity.ok().body(response);
    }


    @Override
    public ResponseEntity<?> deleteAllContacts() {
        contactRepository.deleteAll();
        return ResponseEntity.ok().body("All contacts were deleted.");
    }

    @Override
    @Transactional
    public void addContact(ContactBaseDTO contactDTO) {
        Contact newContact = contactMapper.contactBaseDtoToContact(contactDTO);
        contactRepository.save(newContact);
    }

    @Override
    public ResponseEntity<?> getAllContacts() {
        List<ContactDTO> allContacts = contactRepository.findAll().stream()
                .map(contactMapper::contactToContactDto)
                .toList();

        return ResponseEntity.ok(allContacts);
    }

    @Override
    public ResponseEntity<Page<ContactDTO>> getAllContactsPaged(Pageable pageable) {
        Page<Contact> contactPage = contactRepository.findAll(pageable);
        Page<ContactDTO> contactDTOPage = contactPage.map(contactMapper::contactToContactDto);
        return ResponseEntity.ok(contactDTOPage);
    }

    @Override
    public ResponseEntity<?> updateContact(Long contactId, ContactBaseDTO contactDTO) {
        return contactRepository.findById(contactId).map(contact -> {
            contactMapper.updateContactFromBaseContactDto(contact, contactDTO);
            Contact updatedContact = contactRepository.save(contact);
            return ResponseEntity.ok(updatedContact);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}