package itschool.crmfinalproject.controller;

import itschool.crmfinalproject.entity.FormData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/filtered")
public class DropdownController {

    private static final List<String> types = Arrays.asList("Type1", "Type2");
    private static final List<String> optionsType1 = Arrays.asList("Material1-1", "Material1-2");
    private static final List<String> optionsType2 = Arrays.asList("Material2-1", "Material2-2");

    @GetMapping("/optionsOne")
    public ResponseEntity<List<String>> getOptionsOne() {
        return ResponseEntity.ok(types);
    }

    @GetMapping("/optionsTwo/{type}")
    public ResponseEntity<List<String>> getOptionsTwo(@PathVariable String type) {
        List<String> response = switch (type) {
            case "Type1" -> optionsType1;
            case "Type2" -> optionsType2;
            default -> List.of(); // Empty list for unknown types
        };
        return ResponseEntity.ok(response);
    }

    @PostMapping("/submitForm")
    public ResponseEntity<String> handleFormSubmission(@RequestBody FormData formData) {
        System.out.println("Received: " + formData.getName() + ", " + formData.getType() + ", " + formData.getMaterial());
        return ResponseEntity.ok("Form data submitted successfully");
    }
}