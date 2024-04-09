package itschool.crmfinalproject.repository;

import itschool.crmfinalproject.entity.app.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findBySectorId(Long sectorId);
}
