package itschool.crmfinalproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.model.contact.ContactBaseDTO;
import itschool.crmfinalproject.model.contact.ContactDTO;
import itschool.crmfinalproject.service.contact.ContactService;
import itschool.crmfinalproject.utility.GenerateResponse;
import jakarta.validation.Valid;
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

    @Operation(summary = "Get a contact by ID", description = "Retrieve a contact's details by their ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the contact", content = @Content(schema = @Schema(implementation = ContactDTO.class)))
    @ApiResponse(responseCode = "404", description = "Contact not found")
    @GetMapping("/{contactId}")
    public ResponseEntity<?> getContact(@PathVariable @Parameter(description = "ID of the contact to retrieve") Long contactId) {
        ContactDTO contact = contactService.getContactById(contactId);
        return ResponseEntity.ok(contact);
    }

    @Operation(summary = "Get all contacts", description = "Retrieve a list of all contacts.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all contacts", content = @Content(schema = @Schema(implementation = List.class)))
    @GetMapping("/all")
    public ResponseEntity<?> getAllContacts() {
        List<ContactDTO> allContacts = contactService.getAllContacts();
        return ResponseEntity.ok(allContacts);
    }

    @Operation(summary = "Get all contacts with pagination", description = "Retrieve a paginated list of all contacts.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all paginated contacts", content = @Content(schema = @Schema(implementation = Pageable.class)))
    @GetMapping("/all/paged")
    public ResponseEntity<?> getAllContactsPaged(@Parameter(description = "Pagination configuration") Pageable pageable) {
        return contactService.getAllContactsPaged(pageable);
    }

    @Operation(summary = "Delete a contact by ID", description = "Delete a contact based on their ID.")
    @ApiResponse(responseCode = "200", description = "Contact deleted successfully")
    @ApiResponse(responseCode = "404", description = "Contact not found")
    @DeleteMapping("/{contactId}")
    public ResponseEntity<String> deleteContact(@PathVariable @Parameter(description = "ID of the contact to delete") Long contactId) throws JsonProcessingException {
        contactService.deleteContactById(contactId);
        return GenerateResponse.success("Contact deleted", null);
    }

    @Operation(summary = "Delete all contacts", description = "Delete all contacts from the database.")
    @ApiResponse(responseCode = "200", description = "All contacts deleted successfully")
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllContacts() throws JsonProcessingException {
        contactService.deleteAllContacts();
        return GenerateResponse.success("Contacts deleted", null);
    }

    @Operation(summary = "Add a new contact", description = "Create a new contact with the provided details.")
    @ApiResponse(responseCode = "201", description = "Contact created successfully")
    @PostMapping
    public ResponseEntity<String> addContact(@Valid @RequestBody(description = "New contact details") ContactBaseDTO newContact) throws JsonProcessingException {
        contactService.addContact(newContact);
        return GenerateResponse.created("Contact Created", newContact);
    }

    @Operation(summary = "Update a contact", description = "Update the details of an existing contact.")
    @ApiResponse(responseCode = "200", description = "Contact updated successfully")
    @ApiResponse(responseCode = "404", description = "Contact not found")
    @PatchMapping("/{contactId}")
    public ResponseEntity<?> updateContact(@PathVariable @Parameter(description = "ID of the contact to update") Long contactId, @RequestBody(description = "Updated contact details") ContactBaseDTO contactDTO) {
        return contactService.updateContact(contactId, contactDTO);
    }
}