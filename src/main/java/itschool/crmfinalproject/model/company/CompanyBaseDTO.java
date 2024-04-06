package itschool.crmfinalproject.model.company;

public record CompanyBaseDTO(
        Long id,
        String name,
        Double evaluation,
        Double incomeFromCompany
) {
}
