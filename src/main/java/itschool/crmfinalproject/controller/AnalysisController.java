package itschool.crmfinalproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.model.analysis.*;
import itschool.crmfinalproject.service.data.DataAggregationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
@Tag(name = "Analysis Service", description = "Delivers advanced data analysis capabilities, executing complex queries for in-depth insights.")
public class AnalysisController {

    private final DataAggregationService dataAggregationService;

    @GetMapping("/aggregated-data")
    @Operation(summary = "Get aggregated data overview", description = "Retrieves a summary of aggregated data across different metrics.")
    public AggregatedDataDTO getAggregatedData() {
        return dataAggregationService.getAggregatedData();
    }

    @GetMapping("/income-event-participation-details")
    @Operation(summary = "Get detailed income and event participation data", description = "Provides income and participation data for events, categorized by event type.")
    public List<IncomeEventParticipationDataDTO> getIncomeEventParticipationData() {
        return dataAggregationService.getIncomeEventParticipationData();
    }

    @GetMapping("/event-participation-details")
    @Operation(summary = "Get event participation details", description = "Fetches detailed participation information for each event.")
    public List<EventParticipationDetailDTO> getEventParticipationDetails() {
        return dataAggregationService.getEventParticipationDetails();
    }

    @GetMapping("/sector-revenue")
    @Operation(summary = "Get sector revenue data", description = "Obtains revenue data by sector, calculated based on event incomes.")
    public List<SectorRevenueDTO> getSectorRevenueByEventIncome() {
        return dataAggregationService.getSectorRevenueByEventIncome();
    }

    @GetMapping("/cross-database-comment-analysis")
    @Operation(summary = "Perform cross-database comment analysis", description = "Analyzes comments across databases, correlating event and contact data.")
    public List<CrossDatabaseCommentAnalysisDTO> getCrossDatabaseCommentAnalysis() {
        return dataAggregationService.getCrossDatabaseCommentAnalysis();
    }
}