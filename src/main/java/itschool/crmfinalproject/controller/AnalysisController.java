package itschool.crmfinalproject.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.model.analysis.IncomeEventParticipationDataDTO;
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

//    private final NoSqlDataRepository noSqlDataRepository;
    private final DataAggregationService dataAggregationService;

    @GetMapping("/income-event-participation")
    public List<IncomeEventParticipationDataDTO> getIncomeEventParticipationData() {
        return dataAggregationService.getIncomeEventParticipationData();
    }
}
