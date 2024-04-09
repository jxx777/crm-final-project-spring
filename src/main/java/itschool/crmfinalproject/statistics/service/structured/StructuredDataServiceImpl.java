package itschool.crmfinalproject.statistics.service.structured;

import itschool.crmfinalproject.statistics.repository.StructuredDataRepository;
import itschool.crmfinalproject.statistics.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StructuredDataServiceImpl implements StructuredDataService {

    private final StructuredDataRepository structuredDataRepository;

    @Override
    public List<ContactsPerCompanyDTO> countContactsPerCompany() {
        return structuredDataRepository.countContactsPerCompany();
    }

    @Override
    public List<CompaniesPerSectorDTO> countCompaniesPerSector() {
        return structuredDataRepository.countCompaniesPerSector();
    }

    @Override
    public List<TopTagsDTO> findTopTagsUsed() {
        return structuredDataRepository.findTopTagsUsed();
    }

    @Override
    public List<AvgCompanyEvaluationPerSectorDTO> avgCompanyEvaluationPerSector() {
        return structuredDataRepository.avgCompanyEvaluationPerSector();
    }

    @Override
    public List<TotalIncomePerSectorDTO> totalIncomePerSector() {
        return structuredDataRepository.totalIncomePerSector();
    }

    @Override
    public List<CompaniesAboveEvaluationThresholdDTO> countCompaniesAboveEvaluationThreshold(Double evaluationThreshold) {
        return structuredDataRepository.countCompaniesAboveEvaluationThreshold(evaluationThreshold);
    }

    @Override
    public List<TopSectorsByCompanyCountDTO> getTopSectorsByCompanyCount() {
        return structuredDataRepository.getTopSectorsByCompanyCount();
    }
}