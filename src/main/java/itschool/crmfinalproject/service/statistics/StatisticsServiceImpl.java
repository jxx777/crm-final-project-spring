package itschool.crmfinalproject.service.statistics;

import itschool.crmfinalproject.model.stats.*;
import itschool.crmfinalproject.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;

    @Override
    public List<ContactsPerCompanyDTO> getContactsPerCompanyStats() {
        return statisticsRepository.countContactsPerCompany();
    }

    @Override
    public List<CompaniesPerSectorDTO> getCompaniesPerSectorStats() {
        return statisticsRepository.countCompaniesPerSector();
    }

    @Override
    public List<TopTagsDTO> getTopTagsUsed() {
        return statisticsRepository.findTopTagsUsed();
    }

    @Override
    public List<AvgCompanyEvaluationPerSectorDTO> getAvgCompanyEvaluationPerSector() {
        return statisticsRepository.avgCompanyEvaluationPerSector();
    }

    @Override
    public List<TotalIncomePerSectorDTO> getTotalIncomePerSector() {
        return statisticsRepository.totalIncomePerSector();
    }

    @Override
    public List<CompaniesAboveEvaluationThresholdDTO> countCompaniesAboveEvaluationThreshold(Double threshold) {
        return statisticsRepository.countCompaniesAboveEvaluationThreshold(threshold);
    }

    @Override
    public List<TopSectorsByCompanyCountDTO> getTopSectorsByCompanyCount() {
        return statisticsRepository.getTopSectorsByCompanyCount();
    }
}