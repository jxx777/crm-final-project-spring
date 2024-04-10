package itschool.crmfinalproject.contacts.service;

import itschool.crmfinalproject.contacts.model.ContactBaseDTO;
import itschool.crmfinalproject.contacts.model.ContactDTO;
import itschool.crmfinalproject.contacts.model.NewContactDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

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
     * @param newContactDTO The information of the contact to add.
     * @return {@link NewContactDTO} Data Object representation of the new contact
     */
    ContactDTO addContact(NewContactDTO newContactDTO);
}