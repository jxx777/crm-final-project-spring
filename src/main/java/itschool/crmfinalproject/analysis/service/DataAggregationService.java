package itschool.crmfinalproject.analysis.service;

import itschool.crmfinalproject.analysis.model.*;

import java.util.List;

/**
 * Interface for data aggregation services, providing methods to aggregate and analyze data from various sources.
 */
public interface DataAggregationService {

    /**
     * Aggregates general data from various entities and compiles them into a single DTO.
     *
     * @return An {@link AggregatedDataDTO} containing aggregated data such as total number of events, contacts, total income, and average contacts per event.
     */
    AggregatedDataDTO getAggregatedData();

    /**
     * Aggregates income and participation data from events.
     *
     * @return A list of {@link IncomeEventParticipationDataDTO} each representing aggregated income and participation data for a particular event category.
     */
    List<IncomeEventParticipationDataDTO> getIncomeEventParticipationData();

    /**
     * Provides detailed information about event participation.
     *
     * @return A list of {@link EventParticipationDetailDTO} with detailed information about each event, including its participation details and associated income.
     */
    List<EventParticipationDetailDTO> getEventParticipationDetails();

    /**
     * Aggregates revenue data by sector based on event income.
     *
     * @return A list of {@link SectorRevenueDTO} each representing the total revenue generated for a sector through events.
     */
    List<SectorRevenueDTO> getSectorRevenueByEventIncome();

    /**
     * Performs a cross-database analysis of comments, correlating data from contacts and events.
     *
     * @return A list of {@link CrossDatabaseCommentAnalysisDTO} each representing an analysis of comments across databases, providing insights into user engagement and interactions.
     */
    List<CrossDatabaseCommentAnalysisDTO> getCrossDatabaseCommentAnalysis();
}
