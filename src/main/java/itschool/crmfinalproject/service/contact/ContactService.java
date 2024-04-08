package itschool.crmfinalproject.service.contact;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import itschool.crmfinalproject.model.contact.ContactBaseDTO;
import itschool.crmfinalproject.model.contact.ContactDTO;
import itschool.crmfinalproject.utility.GenerateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ContactService {

    /**
     * Retrieves a contact by their ID.
     *
     * @param contactId The ID of the contact to retrieve.
     * @return A {@link ContactDTO} with the contact's data.
     */
    ContactDTO getContactById(Long contactId);

    /**
     * Retrieves all contacts with pagination.
     *
     * @param pageable The pagination information.
     * @return A {@link ResponseEntity} with a page of contacts.
     */
    ResponseEntity<Page<ContactDTO>> getAllContactsPaged(Pageable pageable);

    /**
     * Retrieves all contacts.
     *
     * @return A list of all {@link ContactDTO}.
     */
    List<ContactDTO> getAllContacts();

    /**
     * Updates a contact's information.
     *
     * @param contactId The ID of the contact to update.
     * @param contactDTO The updated contact information.
     * @return A {@link ResponseEntity} with the updated contact's data.
     */
    ResponseEntity<?> updateContact(Long contactId, ContactBaseDTO contactDTO);

    /**
     * Deletes a contact by their ID.
     *
     * @param contactId The ID of the contact to delete.
     */
    void deleteContactById(Long contactId);

    /**
     * Deletes all contacts.
     */
    void deleteAllContacts();

    /**
     * Adds a new contact.
     *
     * @param contactDTO The information of the contact to add.
     */
    void addContact(ContactBaseDTO contactDTO);

//    @Operation(summary = "Remove a contact from company", description = "Associate a contact with a company.")
//    @ApiResponse(responseCode = "201", description = "Contact removed from company successfully")
//    @ApiResponse(responseCode = "404", description = "Company or contact not found")
//    @PatchMapping("/removecompany/{companyId}/{contactId}")
//    public ResponseEntity<?> removeContactFromCompany(@PathVariable @Parameter(description = "Company id") Long companyId,
//                                                      @PathVariable @Parameter(description = "Contact id to remove") Long contactId
//    ) throws JsonProcessingException {
//        companyService.removeContactFromCompany(companyId, contactId);
//        return GenerateResponse.success("Contact removed!", null);
//    }
}