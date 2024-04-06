package itschool.crmfinalproject.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.service.analysis.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
@Tag(name = "Analysis Service", description = "Delivers advanced data analysis capabilities, executing complex queries for in-depth insights.")
public class AnalysisController {

    private final AnalysisService analysisService;

    // Endpoint methods that call the analysisService to get the data
}
