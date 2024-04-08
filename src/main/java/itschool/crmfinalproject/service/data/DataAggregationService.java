package itschool.crmfinalproject.service.data;

import itschool.crmfinalproject.model.analysis.IncomeEventParticipationDataDTO;

import java.util.List;

public interface DataAggregationService {

//    AggregatedDataDTO getAggregatedData();

    List<IncomeEventParticipationDataDTO> getIncomeEventParticipationData();
    // You can add more method signatures here based on your requirements
}