package itschool.crmfinalproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import itschool.crmfinalproject.model.analysis.*;
import itschool.crmfinalproject.model.stats.*;
import itschool.crmfinalproject.service.data.nosqldata.NoSqlDataService;
import itschool.crmfinalproject.service.data.sqldata.SqlDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@Tag(name = "Statistics Service", description = "Provides statistical insights and data aggregation, supporting decision-making processes.")
public class StatisticsController {

    private final SqlDataService sqlDataService;
    private final NoSqlDataService noSqlDataService;

    /**
     * SQL Database methods using JPQL via the '@Query' annotation.
     * These endpoints fetch data using SQL-based queries.
     */
    @Operation(summary = "Get contacts per company stats", description = "Retrieve a list of contacts count per company.")
    @GetMapping("/contacts-per-company")
    public List<ContactsPerCompanyDTO> getContactsPerCompanyStats() {
        return sqlDataService.countContactsPerCompany();
    }

    @Operation(summary = "Get companies per sector stats", description = "Retrieve a list of companies count per sector.")
    @GetMapping("/companies-per-sector")
    public List<CompaniesPerSectorDTO> getCompaniesPerSectorStats() {
        return sqlDataService.countCompaniesPerSector();
    }

    @Operation(summary = "Get top tags used", description = "Retrieve a list of the most used tags across contacts.")
    @GetMapping("/top-tags")
    public List<TopTagsDTO> getTopTagsUsed() {
        return sqlDataService.findTopTagsUsed();
    }

    @Operation(summary = "Get average company evaluation per sector", description = "Retrieve the average company evaluation for each sector.")
    @GetMapping("/average-evaluation-per-sector")
    public List<AvgCompanyEvaluationPerSectorDTO> getAvgCompanyEvaluationPerSector() {
        return sqlDataService.avgCompanyEvaluationPerSector();
    }

    @Operation(summary = "Get total income per sector", description = "Retrieve the total income for each sector.")
    @GetMapping("/total-income-per-sector")
    public List<TotalIncomePerSectorDTO> getTotalIncomePerSector() {
        return sqlDataService.totalIncomePerSector();
    }

    @Operation(summary = "Get companies above evaluation threshold", description = "Retrieve companies that have an evaluation above a specified threshold.")
    @GetMapping("/companies-above-evaluation-threshold/{evaluationThreshold}")
    public List<CompaniesAboveEvaluationThresholdDTO> getCompaniesAboveEvaluationThreshold(@PathVariable @Parameter(description = "Evaluation greater than passed sum", example = "50,0000") Double evaluationThreshold) {
        return sqlDataService.countCompaniesAboveEvaluationThreshold(evaluationThreshold); // Example threshold
    }

    @Operation(summary = "Get top sectors by company count", description = "Retrieve sectors ordered by the number of companies they contain.")
    @GetMapping("/top-sectors-by-company-count")
    public List<TopSectorsByCompanyCountDTO> getTopSectorsByCompanyCount() {
        return sqlDataService.getTopSectorsByCompanyCount();
    }


    /**
     * NoSQL Data Service methods using the @Aggregation annotation.
     * These endpoints fetch data using NoSQL-based queries.
     */
    @Operation(summary = "Get category comments count", description = "Retrieve a count of comments per event category.")
    @GetMapping("/category-comments-count")
    public List<CategoryCommentsCountDTO> countCommentsPerEventCategory() {
        return noSqlDataService.countCommentsPerEventCategory();
    }

    @Operation(summary = "Find top active users", description = "Retrieve the top users based on the number of comments made.")
    @GetMapping("/top-active-users")
    public List<UserCommentsCountDTO> findTopActiveUsers() {
        return noSqlDataService.findTopActiveUsers();
    }

    @Operation(summary = "Find event popularity over time", description = "Retrieve data on how event popularity varies over time.")
    @GetMapping("/event-popularity-over-time")
    public List<EventPopularityDTO> findEventPopularityOverTime() {
        return noSqlDataService.findEventPopularityOverTime();
    }

    @Operation(summary = "Analyze comment engagement", description = "Retrieve analysis on the engagement of comments based on likes and replies.")
    @GetMapping("/comment-engagement")
    public List<CommentEngagementDTO> analyzeCommentEngagement() {
        return noSqlDataService.analyzeCommentEngagement();
    }

    @Operation(summary = "Analyze comment length distribution", description = "Retrieve the distribution of comment lengths.")
    @GetMapping("/comment-length-distribution")
    public List<CommentLengthDistributionDTO> analyzeCommentLengthDistribution() {
        return noSqlDataService.analyzeCommentLengthDistribution();
    }
}