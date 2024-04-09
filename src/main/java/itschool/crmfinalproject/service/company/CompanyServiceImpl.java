package itschool.crmfinalproject.service.company;

import com.fasterxml.jackson.core.JsonProcessingException;
import itschool.crmfinalproject.entity.app.Company;
import itschool.crmfinalproject.entity.app.Contact;
import itschool.crmfinalproject.exceptions.CompanyNotFoundException;
import itschool.crmfinalproject.mapper.CompanyMapper;
import itschool.crmfinalproject.model.company.CompanyBaseDTO;
import itschool.crmfinalproject.model.company.CompanyDTO;
import itschool.crmfinalproject.repository.CompanyRepository;
import itschool.crmfinalproject.repository.ContactRepository;
import itschool.crmfinalproject.utility.GenerateResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ContactRepository contactRepository;

    private final CompanyMapper companyMapper;


    @Override
    public ResponseEntity<?> getCompanyById(Long companyId) {
        return companyRepository.findById(companyId).map(companyMapper::toCompanyBaseDTO).map(ResponseEntity::ok)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with ID: " + companyId));
    }

    @Override
    public CompanyDTO getFullCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with ID: " + companyId));
        return companyMapper.toCompanyDTO(company);
    }

    @Override
    public List<CompanyBaseDTO> getAllCompanies() {
        return companyRepository.findAll().stream().map(companyMapper::toCompanyBaseDTO).toList();
    }

    @Override
    public ResponseEntity<Page<CompanyBaseDTO>> getAllCompaniesPaged(Pageable pageable) {
        Page<Company> contactPage = companyRepository.findAll(pageable);
        Page<CompanyBaseDTO> contactDTOPage = contactPage.map(companyMapper::toCompanyBaseDTO);
        return ResponseEntity.ok(contactDTOPage);
    }

    @Transactional
    public void addContactToCompany(Long companyId, Long contactId) throws JsonProcessingException {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException("Company not found"));
        Contact contact = contactRepository.findById(contactId).orElseThrow(() -> new CompanyNotFoundException("Contact not found"));

        company.addContact(contact);
        companyRepository.save(company);

        GenerateResponse.success("Contact added to the company", company);
    }

    @Transactional
    public void deleteCompany(Long companyId) {
        companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException("Company not found with ID: " + companyId));
        companyRepository.deleteById(companyId);
    }
}