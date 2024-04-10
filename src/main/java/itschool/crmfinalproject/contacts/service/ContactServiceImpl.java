package itschool.crmfinalproject.contacts.service;

import itschool.crmfinalproject.contacts.entity.Contact;
import itschool.crmfinalproject.contacts.mapper.ContactMapper;
import itschool.crmfinalproject.contacts.model.ContactBaseDTO;
import itschool.crmfinalproject.contacts.model.ContactDTO;
import itschool.crmfinalproject.contacts.model.NewContactDTO;
import itschool.crmfinalproject.contacts.repository.ContactRepository;
import itschool.crmfinalproject.exceptions.ContactNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Override
    public ContactDTO getContactById(Long contactId) {
        return contactRepository.findById(contactId)
                .map(contactMapper::toContactDTO)
                .orElseThrow(() -> new ContactNotFoundException("Contact with the provided ID does not exist")
        );
    }

    @Override
    public void deleteContactById(Long contactId) {
        if (!contactRepository.existsById(contactId)) {
            throw new ContactNotFoundException("Contact not found with ID: " + contactId);
        }
        contactRepository.deleteById(contactId);
    }


    @Override
    public void deleteAllContacts() {
        contactRepository.deleteAll();
    }

    @Override
    @Transactional
    public NewContactDTO addContact(NewContactDTO newContactDTO) {
        Contact newContact = contactMapper.newToContact(newContactDTO);
        contactRepository.save(newContact);

        return contactMapper.contactToNewContactDTO(newContact);
    }

    @Override
    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(contactMapper::toContactDTO)
                .toList();
    }

    @Override
    public ResponseEntity<Page<ContactDTO>> getAllContactsPaged(Pageable pageable) {
        Page<Contact> contactPage = contactRepository.findAll(pageable);
        Page<ContactDTO> contactDTOPage = contactPage.map(contactMapper::toContactDTO);
        return ResponseEntity.ok(contactDTOPage);
    }

    @Override
    public ResponseEntity<?> updateContact(Long contactId, ContactBaseDTO contactDTO) {
        return contactRepository.findById(contactId).map(contact -> {
            contactMapper.updateBaseContactDTO(contact, contactDTO);
            Contact updatedContact = contactRepository.save(contact);
            return ResponseEntity.ok(updatedContact);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}