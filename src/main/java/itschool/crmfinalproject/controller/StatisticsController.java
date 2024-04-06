package itschool.crmfinalproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.model.stats.*;
import itschool.crmfinalproject.service.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@Tag(name = "Statistics Service", description = "Provides statistical insights and data aggregation, supporting decision-making processes.")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Operation(summary = "Get contacts per company stats", description = "Retrieve a list of contacts count per company.")
    @GetMapping("/contacts-per-company")
    public List<ContactsPerCompanyDTO> getContactsPerCompanyStats() {
        return statisticsService.getContactsPerCompanyStats();
    }

    @Operation(summary = "Get companies per sector stats", description = "Retrieve a list of companies count per sector.")
    @GetMapping("/companies-per-sector")
    public List<CompaniesPerSectorDTO> getCompaniesPerSectorStats() {
        return statisticsService.getCompaniesPerSectorStats();
    }

    @Operation(summary = "Get top tags used", description = "Retrieve a list of the most used tags across contacts.")
    @GetMapping("/top-tags")
    public List<TopTagsDTO> getTopTagsUsed() {
        return statisticsService.getTopTagsUsed();
    }

    @Operation(summary = "Get average company evaluation per sector", description = "Retrieve the average company evaluation for each sector.")
    @GetMapping("/average-evaluation-per-sector")
    public List<AvgCompanyEvaluationPerSectorDTO> getAvgCompanyEvaluationPerSector() {
        return statisticsService.getAvgCompanyEvaluationPerSector();
    }

    @Operation(summary = "Get total income per sector", description = "Retrieve the total income for each sector.")
    @GetMapping("/total-income-per-sector")
    public List<TotalIncomePerSectorDTO> getTotalIncomePerSector() {
        return statisticsService.getTotalIncomePerSector();
    }

    @Operation(summary = "Get companies above evaluation threshold", description = "Retrieve companies that have an evaluation above a specified threshold.")
    @GetMapping("/companies-above-evaluation-threshold")
    public List<CompaniesAboveEvaluationThresholdDTO> getCompaniesAboveEvaluationThreshold() {
        return statisticsService.countCompaniesAboveEvaluationThreshold(10000.0); // Example threshold
    }

    @Operation(summary = "Get top sectors by company count", description = "Retrieve sectors ordered by the number of companies they contain.")
    @GetMapping("/top-sectors-by-company-count")
    public List<TopSectorsByCompanyCountDTO> getTopSectorsByCompanyCount() {
        return statisticsService.getTopSectorsByCompanyCount();
    }
}