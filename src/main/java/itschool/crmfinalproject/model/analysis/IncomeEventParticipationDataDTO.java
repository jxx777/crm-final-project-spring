package itschool.crmfinalproject.model.analysis;

import java.util.Set;

public record IncomeEventParticipationDataDTO(
        String eventCategory,
        Double totalIncome,
        Integer totalParticipants,
        Set<String> participatingCompanies
) {
}