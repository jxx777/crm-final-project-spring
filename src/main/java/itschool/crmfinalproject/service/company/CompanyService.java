package itschool.crmfinalproject.service.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import itschool.crmfinalproject.model.company.CompanyDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CompanyService {
    ResponseEntity<?> getCompanyById(Long id) throws JsonProcessingException;
    CompanyDTO getFullCompanyById(Long id) throws JsonProcessingException;
    ResponseEntity<?> getAllCompanies() throws JsonProcessingException;
    ResponseEntity<?> getAllCompaniesPaged(Pageable pageable) throws JsonProcessingException;
    ResponseEntity<?> addContactToCompany(Long companyId, Long contactId);
}