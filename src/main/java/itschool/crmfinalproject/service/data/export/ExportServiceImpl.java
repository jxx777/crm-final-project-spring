package itschool.crmfinalproject.service.data.export;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itschool.crmfinalproject.entity.app.Company;
import itschool.crmfinalproject.entity.app.Contact;
import itschool.crmfinalproject.entity.user.User;
import itschool.crmfinalproject.exceptions.UnsupportedEntityTypeException;
import itschool.crmfinalproject.exceptions.UnsupportedFormatException;
import itschool.crmfinalproject.mapper.CompanyMapper;
import itschool.crmfinalproject.mapper.ContactMapper;
import itschool.crmfinalproject.mapper.UserMapper;
import itschool.crmfinalproject.model.company.CompanyDTO;
import itschool.crmfinalproject.model.contact.ContactBaseDTO;
import itschool.crmfinalproject.model.contact.ContactDTO;
import itschool.crmfinalproject.model.user.UserDTO;
import itschool.crmfinalproject.repository.CompanyRepository;
import itschool.crmfinalproject.repository.ContactRepository;
import itschool.crmfinalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;

    private final ObjectMapper objectMapper;
    private final ContactMapper contactMapper;
    private final CompanyMapper companyMapper;
    private final UserMapper userMapper;

    /**
     * Exports data based on the specified entity type and format.
     *
     * @param entityType The type of entity to export. Can be "users", "contacts", or "companies".
     * @param format     The format to export the data in. Can be "csv" or "json".
     * @return A {@link ResponseEntity} containing the exported data as a byte array.
     * @throws JsonProcessingException        If an error occurs during JSON processing.
     * @throws UnsupportedEntityTypeException If the specified entity type is not supported.
     * @throws UnsupportedFormatException     If the specified format is not supported.
     */
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> exportData(String entityType, String format) throws JsonProcessingException {
        return switch (format.toLowerCase()) {
            case "csv" -> switch (entityType) {
                case "users" -> exportUsersToCsv();
                case "contacts" -> exportContactsToCsv();
                case "companies" -> exportCompaniesToCsv();
                default -> throw new UnsupportedEntityTypeException(entityType);
            };
            case "json" -> switch (entityType) {
                case "users" -> exportUsersToJson();
                case "contacts" -> exportContactsToJson();
                case "companies" -> exportCompaniesToJson();
                default -> throw new UnsupportedEntityTypeException(entityType);
            };
            default -> throw new UnsupportedFormatException(format);
        };
    }

    // CSV - Can be used in reports, spreadsheets..
    private <T> byte[] exportListToCsv(List<T> dataList, Function<T, String> mapper, String header) {
        String csvContent = dataList.stream().map(mapper).collect(Collectors.joining("\n", header + "\n", ""));
        return csvContent.getBytes();
    }

    private ResponseEntity<byte[]> buildCsvResponse(byte[] csvBytes, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        headers.setContentLength(csvBytes.length);

        return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("text/csv")).body(csvBytes);
    }

    private ResponseEntity<byte[]> exportUsersToCsv() {
        List<UserDTO> exportedUsers = userRepository.findAll().stream()
                .map(userMapper::toUserDto)
                .toList();

        byte[] csvBytes = exportListToCsv(exportedUsers,
                user -> MessageFormat.format("{0},{1},{2}",
                        user.id(),
                        user.username(),
                        user.email()
                ),
                "ID,Username,Email");

        return buildCsvResponse(csvBytes, "users.csv");
    }

    private ResponseEntity<byte[]> exportContactsToCsv() {
        List<ContactDTO> exportedContacts = contactRepository.findAll().stream()
                .map(contactMapper::toContactDTO)
                .toList();

        byte[] csvBytes = exportListToCsv(exportedContacts,
                contact -> MessageFormat.format("{0},{1},{2},{3},{4}",
                        contact.id(),
                        contact.firstName(),
                        contact.lastName(),
                        contact.email(),
                        contact.phoneNumber()
                ),
                "ID,FirstName,LastName,Email,PhoneNumber");

        return buildCsvResponse(csvBytes, "contacts.csv");
    }

    private ResponseEntity<byte[]> exportCompaniesToCsv() {
        List<CompanyDTO> exportedCompanies = companyRepository.findAll().stream()
                .map(companyMapper::toCompanyDTO)
                .toList();

        byte[] csvBytes = exportListToCsv(exportedCompanies,
                company -> MessageFormat.format("{0},{1},{2},{3},{4}",
                        company.id(),
                        company.name(),
                        company.evaluation(),
                        company.incomeFromCompany(),
                        company.contacts().stream().map(ContactBaseDTO::lastName)
                ),
                "ID,CompanyName,Evaluation,Income,Contacts");

        return buildCsvResponse(csvBytes, "contacts.csv");
    }


    // TODO: JSON - Still on the fence if providing a JSON file (not a response) is redundant, maybe we want to export this way? To be decided - Also, we need make a DTO out of it?
    private String convertListToJson(List<?> dataList) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dataList);
    }

    private ResponseEntity<byte[]> buildJsonResponse(byte[] jsonBytes, String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        headers.setContentLength(jsonBytes.length);

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_JSON).body(jsonBytes);
    }

    private ResponseEntity<byte[]> exportUsersToJson() throws JsonProcessingException {
        List<User> users = userRepository.findAll();
        String json = convertListToJson(users);
        return buildJsonResponse(json.getBytes(), "users.json");
    }

    private ResponseEntity<byte[]> exportContactsToJson() throws JsonProcessingException {
        List<Contact> contacts = contactRepository.findAll();
        String json = convertListToJson(contacts);
        return buildJsonResponse(json.getBytes(), "contacts.json");
    }

    private ResponseEntity<byte[]> exportCompaniesToJson() throws JsonProcessingException {
        List<Company> contacts = companyRepository.findAll();
        String json = convertListToJson(contacts);
        return buildJsonResponse(json.getBytes(), "contacts.json");
    }
}