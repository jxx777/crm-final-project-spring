package itschool.crmfinalproject.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import itschool.crmfinalproject.model.contact.ContactBaseDTO;
import itschool.crmfinalproject.model.contact.ContactDTO;
import itschool.crmfinalproject.service.contact.ContactService;
import itschool.crmfinalproject.utility.GenerateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @SneakyThrows
    @GetMapping("/{contactId}")
    public ResponseEntity<?> getContact(@PathVariable Long contactId) {
        return contactService.getContactById(contactId);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllContacts() {
        List<ContactDTO> allContacts = contactService.getAllContacts();
        return ResponseEntity.ok(allContacts);
    }

    @GetMapping("/all/paged")
    public ResponseEntity<?> getAllContactsPaged(Pageable pageable) {
        return contactService.getAllContactsPaged(pageable);
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<String> deleteContact(@PathVariable Long contactId) throws JsonProcessingException {
        contactService.deleteContactById(contactId);
        return GenerateResponse.success("Contact deleted", null);
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllContacts() throws JsonProcessingException {
        contactService.deleteAllContacts();
        return GenerateResponse.success("Contacts deleted", null);
    }

    @PostMapping
    public ResponseEntity<String> addContact(@Valid @RequestBody ContactBaseDTO newContact) throws JsonProcessingException {
        contactService.addContact(newContact);
        return GenerateResponse.created("Contact Created", newContact);
    }

    @PatchMapping("/{contactId}")
    public ResponseEntity<?> updateContact(@PathVariable Long contactId, @RequestBody ContactBaseDTO contactDTO) {
        return contactService.updateContact(contactId, contactDTO);
    }
}