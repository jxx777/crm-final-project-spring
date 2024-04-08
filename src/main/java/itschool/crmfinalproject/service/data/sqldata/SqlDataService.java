package itschool.crmfinalproject.service.data.sqldata;

import itschool.crmfinalproject.model.stats.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlDataService {

    List<ContactsPerCompanyDTO> countContactsPerCompany();

    List<CompaniesPerSectorDTO> countCompaniesPerSector();

    List<TopTagsDTO> findTopTagsUsed();

    List<AvgCompanyEvaluationPerSectorDTO> avgCompanyEvaluationPerSector();

    List<TotalIncomePerSectorDTO> totalIncomePerSector();

    List<CompaniesAboveEvaluationThresholdDTO> countCompaniesAboveEvaluationThreshold(Double evaluationThreshold);

    List<TopSectorsByCompanyCountDTO> getTopSectorsByCompanyCount();
}