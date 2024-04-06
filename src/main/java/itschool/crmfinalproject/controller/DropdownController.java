package itschool.crmfinalproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.entity.FormData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/filtered")
@Tag(name = "Dropdown Management", description = "Provides endpoints for retrieving dropdown menu data, including types and associated options.")
public class DropdownController {

    private static final List<String> types = Arrays.asList("Type1", "Type2");
    private static final List<String> optionsType1 = Arrays.asList("Material1-1", "Material1-2");
    private static final List<String> optionsType2 = Arrays.asList("Material2-1", "Material2-2");

    @Operation(summary = "Get dropdown options for types", description = "Retrieve a list of available types for the dropdown.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved types")
    @GetMapping("/optionsOne")
    public ResponseEntity<List<String>> getOptionsOne() {
        return ResponseEntity.ok(types);
    }

    @Operation(summary = "Get dropdown options based on type", description = "Retrieve specific options based on the selected type.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved options")
    @ApiResponse(responseCode = "400", description = "Invalid type provided")
    @GetMapping("/optionsTwo/{type}")
    public ResponseEntity<List<String>> getOptionsTwo(@PathVariable @Parameter(description = "The type for which to fetch options") String type) {
        List<String> response = switch (type) {
            case "Type1" -> optionsType1;
            case "Type2" -> optionsType2;
            default -> List.of(); // Empty list for unknown types
        };
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Handle form submission", description = "Processes the submitted form data.")
    @ApiResponse(responseCode = "200", description = "Form data submitted successfully")
    @PostMapping("/submitForm")
    public ResponseEntity<String> handleFormSubmission(@RequestBody FormData formData) {
        System.out.println("Received: " + formData.getName() + ", " + formData.getType() + ", " + formData.getMaterial());
        return ResponseEntity.ok("Form data submitted successfully");
    }
}
