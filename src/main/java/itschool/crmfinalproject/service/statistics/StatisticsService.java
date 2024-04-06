package itschool.crmfinalproject.service.statistics;

import itschool.crmfinalproject.model.stats.*;

import java.util.List;

public interface StatisticsService {

    List<ContactsPerCompanyDTO> getContactsPerCompanyStats();

    List<CompaniesPerSectorDTO> getCompaniesPerSectorStats();

    List<TopTagsDTO> getTopTagsUsed();

    List<AvgCompanyEvaluationPerSectorDTO> getAvgCompanyEvaluationPerSector();

    List<TotalIncomePerSectorDTO> getTotalIncomePerSector();

    List<CompaniesAboveEvaluationThresholdDTO> countCompaniesAboveEvaluationThreshold(Double threshold);

    List<TopSectorsByCompanyCountDTO> getTopSectorsByCompanyCount();
}