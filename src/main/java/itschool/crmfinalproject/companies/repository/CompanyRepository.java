package itschool.crmfinalproject.companies.repository;

import itschool.crmfinalproject.companies.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findBySectorId(Long sectorId);
}
