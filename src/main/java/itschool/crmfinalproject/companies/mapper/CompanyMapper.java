package itschool.crmfinalproject.companies.mapper;

import itschool.crmfinalproject.companies.entity.Company;
import itschool.crmfinalproject.companies.model.CompanyBaseDTO;
import itschool.crmfinalproject.companies.model.CompanyDTO;
import itschool.crmfinalproject.contacts.mapper.ContactMapper;
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