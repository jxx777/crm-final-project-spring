package itschool.crmfinalproject.mapper;

import itschool.crmfinalproject.entity.app.Company;
import itschool.crmfinalproject.model.company.CompanyBaseDTO;
import itschool.crmfinalproject.model.company.CompanyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ContactMapper.class)
public interface CompanyMapper {
    CompanyBaseDTO toCompanyBaseDTO(Company company);

    Company toCompany(CompanyBaseDTO companyDTO);

    @Mapping(target = "contacts", source = "contacts")
    CompanyDTO toCompanyDTO(Company company);

    Company toCompany(CompanyDTO companyDTO);
}