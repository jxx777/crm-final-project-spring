package itschool.crmfinalproject.contacts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.common.utility.GenerateResponse;
import itschool.crmfinalproject.contacts.model.ContactBaseDTO;
import itschool.crmfinalproject.contacts.model.ContactDTO;
import itschool.crmfinalproject.contacts.model.NewContactDTO;
import itschool.crmfinalproject.contacts.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
@Tag(name = "Contact Service", description = "Facilitates CRUD operations for contact entities, offering detailed data access and management.")
public class ContactController {

    private final ContactService contactService;

    @Operation(summary = "Get a contact by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the contact")
    @GetMapping("/{contactId}")
    public ResponseEntity<?> getContact(@PathVariable @Parameter(description = "ID of the contact to retrieve", example = "1") Long contactId) {
        ContactDTO contact = contactService.getContactById(contactId);
        return ResponseEntity.ok(contact);
    }

    @Operation(summary = "Get all contacts")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all contacts")
    @GetMapping("/all")
    public ResponseEntity<?> getAllContacts() {
        List<ContactDTO> allContacts = contactService.getAllContacts();
        return ResponseEntity.ok(allContacts);
    }

    @Operation(summary = "Get all contacts with pagination")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all paginated contacts")
    @GetMapping("/all/paged")
    public ResponseEntity<?> getAllContactsPaged(@Parameter(description = "Pagination configuration") Pageable pageable) {
        return contactService.getAllContactsPaged(pageable);
    }

    @Operation(summary = "Delete a contact by ID")
    @ApiResponse(responseCode = "200", description = "Contact deleted successfully")
    @DeleteMapping("/{contactId}")
    public ResponseEntity<?> deleteContact(@PathVariable @Parameter(description = "ID of the contact to delete", example = "2") Long contactId) throws JsonProcessingException {
        contactService.deleteContactById(contactId);
        return GenerateResponse.success("Contact deleted", null);
    }

    @Operation(summary = "Delete all contacts")
    @ApiResponse(responseCode = "200", description = "All contacts deleted successfully")
    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllContacts() throws JsonProcessingException {
        contactService.deleteAllContacts();
        return GenerateResponse.success("All contacts deleted", null);
    }

    @Operation(summary = "Add a new contact")
    @ApiResponse(responseCode = "201", description = "Contact created successfully")
    @PostMapping("/add")
    public ResponseEntity<?> addContact(@RequestBody(description = "New contact details", content = @Content(schema = @Schema(implementation = NewContactDTO.class))) NewContactDTO newContact
    ) throws JsonProcessingException {
        ContactDTO createdContact = contactService.addContact(newContact);
        return GenerateResponse.created("Contact Created", createdContact);
    }

    @Operation(summary = "Update a contact")
    @ApiResponse(responseCode = "200", description = "Contact updated successfully")
    @PatchMapping("/{contactId}")
    public ResponseEntity<?> updateContact(
            @PathVariable @Parameter(description = "ID of the contact to update", example = "3") Long contactId,
            @RequestBody(description = "Updated contact fieldDetails", content = @Content(schema = @Schema(implementation = ContactBaseDTO.class))) ContactBaseDTO contactToUpdateBaseDTO
    ) {
        return contactService.updateContact(contactId, contactToUpdateBaseDTO);
    }
}