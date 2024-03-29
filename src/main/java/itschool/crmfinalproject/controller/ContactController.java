package itschool.crmfinalproject.controller;


import itschool.crmfinalproject.model.contact.ContactBaseDTO;
import itschool.crmfinalproject.service.contact.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return contactService.getAllContacts();
    }

    @GetMapping("/all/paged")
    public ResponseEntity<?> getAllContactsPaged(Pageable pageable) {
        return contactService.getAllContactsPaged(pageable);
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<?> deleteContact(@PathVariable Long contactId) {
        return contactService.deleteContactById(contactId);
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllContacts() {
        return contactService.deleteAllContacts();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addContact(@Valid @RequestBody ContactBaseDTO newContact) {
        contactService.addContact(newContact);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{contactId}")
    public ResponseEntity<?> updateContact(@PathVariable Long contactId, @RequestBody ContactBaseDTO contactDTO) {
        return contactService.updateContact(contactId, contactDTO);
    }
}