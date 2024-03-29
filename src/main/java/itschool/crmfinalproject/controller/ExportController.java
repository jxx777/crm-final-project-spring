package itschool.crmfinalproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import itschool.crmfinalproject.service.export.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/export")
public class ExportController {

    private final ExportService exportService;

    @GetMapping("/{entity}")
    public ResponseEntity<?> exportData(
            @PathVariable String entity,
            @RequestParam(required = false, defaultValue = "csv") String format) throws JsonProcessingException {
        return exportService.exportData(entity, format);
    }
}