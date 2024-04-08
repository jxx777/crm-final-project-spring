package itschool.crmfinalproject.service.data.sqldata;

import itschool.crmfinalproject.model.stats.*;
import itschool.crmfinalproject.repository.SqlDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SqlDataServiceImpl implements SqlDataService {

    private final SqlDataRepository sqlDataRepository;

    @Override
    public List<ContactsPerCompanyDTO> countContactsPerCompany() {
        return sqlDataRepository.countContactsPerCompany();
    }

    @Override
    public List<CompaniesPerSectorDTO> countCompaniesPerSector() {
        return sqlDataRepository.countCompaniesPerSector();
    }

    @Override
    public List<TopTagsDTO> findTopTagsUsed() {
        return sqlDataRepository.findTopTagsUsed();
    }

    @Override
    public List<AvgCompanyEvaluationPerSectorDTO> avgCompanyEvaluationPerSector() {
        return sqlDataRepository.avgCompanyEvaluationPerSector();
    }

    @Override
    public List<TotalIncomePerSectorDTO> totalIncomePerSector() {
        return sqlDataRepository.totalIncomePerSector();
    }

    @Override
    public List<CompaniesAboveEvaluationThresholdDTO> countCompaniesAboveEvaluationThreshold(Double evaluationThreshold) {
        return sqlDataRepository.countCompaniesAboveEvaluationThreshold(evaluationThreshold);
    }

    @Override
    public List<TopSectorsByCompanyCountDTO> getTopSectorsByCompanyCount() {
        return sqlDataRepository.getTopSectorsByCompanyCount();
    }
}