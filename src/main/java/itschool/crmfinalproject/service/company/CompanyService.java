package itschool.crmfinalproject.service.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import itschool.crmfinalproject.model.company.CompanyBaseDTO;
import itschool.crmfinalproject.model.company.CompanyDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Interface defining the business logic for company-related operations.
 */
public interface CompanyService {

    /**
     * Retrieves a company by its ID.
     *
     * @param id The unique identifier of the company.
     * @return A {@link ResponseEntity} containing a {@link CompanyBaseDTO}.
     */
    ResponseEntity<?> getCompanyById(Long id);

    /**
     * Retrieves detailed information about a company by its ID.
     *
     * @param id The unique identifier of the company.
     * @return A {@link CompanyDTO} with detailed information.
     */
    CompanyDTO getFullCompanyById(Long id);

    /**
     * Retrieves all companies.
     *
     * @return A list of {@link CompanyBaseDTO} objects.
     */
    List<CompanyBaseDTO> getAllCompanies();

    /**
     * Retrieves all companies with pagination.
     *
     * @param pageable The pagination information.
     * @return A paginated list of companies as {@link CompanyBaseDTO} objects.
     */
    ResponseEntity<?> getAllCompaniesPaged(Pageable pageable);

    /**
     * Adds a contact to a company.
     *
     * @param companyId The ID of the company to which the contact will be added.
     * @param contactId The ID of the contact to be added to the company.
     */
    void addContactToCompany(Long companyId, Long contactId) throws JsonProcessingException;

    /**
     * Removes company from the database.
     *
     * @param companyId The ID of the company.
     */
    void deleteCompany(Long companyId);
}