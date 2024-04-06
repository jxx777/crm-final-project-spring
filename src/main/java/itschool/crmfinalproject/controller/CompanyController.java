package itschool.crmfinalproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.model.company.CompanyBaseDTO;
import itschool.crmfinalproject.model.company.CompanyDTO;
import itschool.crmfinalproject.service.company.CompanyService;
import itschool.crmfinalproject.utility.GenerateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
@Tag(name = "Company Service", description = "Manages operations related to company entities, providing access and modification capabilities.")
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "Get a company by ID", description = "Fetch a company by its unique identifier.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the company", content = @Content(schema = @Schema(implementation = CompanyDTO.class)))
    @ApiResponse(responseCode = "404", description = "Company not found")
    @GetMapping("/{companyId}")
    public ResponseEntity<?> getCompanyById(@PathVariable @Parameter(description = "The unique identifier of the company") Long companyId) {
        return companyService.getCompanyById(companyId);
    }

    @Operation(summary = "Get detailed information about a company", description = "Fetch detailed information about a company including associated contacts and events.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved detailed company information", content = @Content(schema = @Schema(implementation = CompanyDTO.class)))
    @ApiResponse(responseCode = "404", description = "Company not found")
    @GetMapping("/full/{companyId}")
    public ResponseEntity<CompanyDTO> getFullCompany(@PathVariable @Parameter(description = "The unique identifier of the company to fetch details for") Long companyId) {
        CompanyDTO fullCompany = companyService.getFullCompanyById(companyId);
        return ResponseEntity.ok(fullCompany);
    }

    @Operation(summary = "Get all companies", description = "Fetch all companies in the database.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all companies", content = @Content(schema = @Schema(implementation = List.class)))
    @GetMapping("/all")
    public ResponseEntity<?> getAllCompanies() {
        List<CompanyBaseDTO> allCompanies = companyService.getAllCompanies();
        return ResponseEntity.ok(allCompanies);
    }

    @Operation(summary = "Get all companies with pagination", description = "Fetch all companies with pagination support.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all companies with pagination", content = @Content(schema = @Schema(implementation = Pageable.class)))
    @GetMapping("/all/paged")
    public ResponseEntity<?> getAllCompaniesPaged(@PageableDefault(size = 15) @Parameter(description = "Pagination configuration") Pageable pageable) {
        return companyService.getAllCompaniesPaged(pageable);
    }

    @Operation(summary = "Add a contact to a company", description = "Associate a contact with a company.")
    @ApiResponse(responseCode = "201", description = "Contact added to the company successfully")
    @ApiResponse(responseCode = "404", description = "Company or contact not found")
    @PatchMapping("/add-contact/{companyId}/{contactId}")
    public ResponseEntity<?> addContactToCompany(@PathVariable @Parameter(description = "The unique identifier of the company") Long companyId,
                                                 @PathVariable @Parameter(description = "The unique identifier of the contact to add") Long contactId) throws JsonProcessingException {
        companyService.addContactToCompany(companyId, contactId);
        return GenerateResponse.created("Contact added!", null);
    }
}