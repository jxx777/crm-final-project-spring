package itschool.crmfinalproject.service.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import itschool.crmfinalproject.model.company.CompanyBaseDTO;
import itschool.crmfinalproject.model.company.CompanyDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompanyService {
    ResponseEntity<?> getCompanyById(Long id) throws JsonProcessingException;
    CompanyDTO getFullCompanyById(Long id) throws JsonProcessingException;
    List<CompanyBaseDTO> getAllCompanies() throws JsonProcessingException;
    ResponseEntity<?> getAllCompaniesPaged(Pageable pageable) throws JsonProcessingException;
    ResponseEntity<?> addContactToCompany(Long companyId, Long contactId);
}