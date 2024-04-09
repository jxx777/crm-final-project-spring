package itschool.crmfinalproject.exports.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/export")
@Tag(name = "Export Service", description = "Enables data export functionality for various entities, supporting multiple file formats.")
public class ExportController {

    private final itschool.crmfinalproject.exports.export.ExportService exportService;

    @SneakyThrows
    @Operation(summary = "Export data", description = "Exports data for the specified entity in the chosen format.")
    @ApiResponse(responseCode = "200", description = "Data exported successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request or unsupported format")
    @GetMapping("/{entity}")
    public ResponseEntity<?> exportData(
            @PathVariable @Parameter(description = "Entity to export") String entity,
            @RequestParam @Parameter(description = "Format of the export, defaults to CSV") String format
    ) {
        return exportService.exportData(entity, format);
    }
}