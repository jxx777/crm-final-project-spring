package itschool.crmfinalproject.mapper;

import itschool.crmfinalproject.entity.app.Company;
import itschool.crmfinalproject.model.company.CompanyBaseDTO;
import itschool.crmfinalproject.model.company.CompanyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ContactMapper.class)
public interface CompanyMapper {
    CompanyBaseDTO companyToCompanyBaseDTO(Company company);

    Company companyDtoBaseToCompany(CompanyBaseDTO companyDTO);

    @Mapping(target = "contacts", source = "contacts")
    CompanyDTO companyToCompanyDTO(Company company);

    Company companyDtoToCompany(CompanyDTO companyDTO);
}