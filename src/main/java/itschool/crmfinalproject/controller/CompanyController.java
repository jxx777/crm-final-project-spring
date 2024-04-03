package itschool.crmfinalproject.controller;

import itschool.crmfinalproject.model.company.CompanyBaseDTO;
import itschool.crmfinalproject.model.company.CompanyDTO;
import itschool.crmfinalproject.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @SneakyThrows
    @GetMapping("/{companyId}")
    public ResponseEntity<?> getCompanyById(@PathVariable Long companyId) {
        return companyService.getCompanyById(companyId);
    }

    @SneakyThrows
    @GetMapping("/full/{companyId}")
    public ResponseEntity<CompanyDTO> getFullCompany(@PathVariable Long companyId) {
        CompanyDTO fullCompany = companyService.getFullCompanyById(companyId);
        return ResponseEntity.ok(fullCompany);
    }

    @SneakyThrows
    @GetMapping("/all")
    public ResponseEntity<?> getAllCompanies() {
        List<CompanyBaseDTO> allCompanies = companyService.getAllCompanies();
        return ResponseEntity.ok(allCompanies);
    }

    @SneakyThrows
    @GetMapping("/all/paged")
    public ResponseEntity<?> getAllCompaniesPaged(@PageableDefault(size = 15) Pageable pageable) {
        return companyService.getAllCompaniesPaged(pageable);
    }

    @PatchMapping("/add-contact/{companyId}/{contactId}")
    public ResponseEntity<?> addContactToCompany(@PathVariable Long companyId, @PathVariable Long contactId) {
        return companyService.addContactToCompany(companyId, contactId);
    }
}