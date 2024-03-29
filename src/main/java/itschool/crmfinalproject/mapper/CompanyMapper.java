package itschool.crmfinalproject.mapper;

import itschool.crmfinalproject.entity.app.Company;
import itschool.crmfinalproject.model.company.CompanyBaseDTO;
import itschool.crmfinalproject.model.company.CompanyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ContactMapper.class)
public interface CompanyMapper {
    CompanyBaseDTO companyToCompanyBaseDTO(Company company);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Company companyDtoBaseToCompany(CompanyBaseDTO companyDTO);

    @Mapping(target = "contacts", source = "contacts")
    CompanyDTO companyToCompanyDTO(Company company);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Company companyDtoToCompany(CompanyDTO companyDTO);
}
