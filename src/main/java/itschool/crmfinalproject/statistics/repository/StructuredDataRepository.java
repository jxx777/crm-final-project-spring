package itschool.crmfinalproject.statistics.repository;

import itschool.crmfinalproject.contacts.entity.Contact;
import itschool.crmfinalproject.statistics.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StructuredDataRepository extends JpaRepository<Contact, Long> {

    @Query("""
    SELECT new itschool.crmfinalproject.statistics.model.ContactsPerCompanyDTO(co.name, COUNT(c))
    FROM Company co JOIN co.contacts c
    GROUP BY co.name
    """)
    List<ContactsPerCompanyDTO> countContactsPerCompany();

    @Query("""
    SELECT new itschool.crmfinalproject.statistics.model.CompaniesPerSectorDTO(s.sectorName, COUNT(co))
    FROM Sector s JOIN s.companies co
    GROUP BY s.sectorName
    """)
    List<CompaniesPerSectorDTO> countCompaniesPerSector();

    @Query("""
    SELECT new itschool.crmfinalproject.statistics.model.TopTagsDTO(tag, COUNT(tag))
    FROM Contact c JOIN c.tags tag
    GROUP BY tag
    ORDER BY COUNT(tag) DESC
    """)
    List<TopTagsDTO> findTopTagsUsed();

    @Query("""
    SELECT new itschool.crmfinalproject.statistics.model.AvgCompanyEvaluationPerSectorDTO(s.sectorName, AVG(co.evaluation))
    FROM Sector s JOIN s.companies co
    GROUP BY s.sectorName
    """)
    List<AvgCompanyEvaluationPerSectorDTO> avgCompanyEvaluationPerSector();

    @Query("""
    SELECT new itschool.crmfinalproject.statistics.model.TotalIncomePerSectorDTO(s.sectorName, SUM(co.incomeFromCompany))
    FROM Sector s JOIN s.companies co
    GROUP BY s.sectorName
    """)
    List<TotalIncomePerSectorDTO> totalIncomePerSector();

    @Query("""
    SELECT new itschool.crmfinalproject.statistics.model.CompaniesAboveEvaluationThresholdDTO(s.sectorName, COUNT(co))
    FROM Sector s JOIN s.companies co
    WHERE co.evaluation > :evaluationThreshold
    GROUP BY s.sectorName
    """)
    List<CompaniesAboveEvaluationThresholdDTO> countCompaniesAboveEvaluationThreshold(@Param("evaluationThreshold") Double threshold);

    @Query("""
    SELECT new itschool.crmfinalproject.statistics.model.TopSectorsByCompanyCountDTO(s.sectorName, COUNT(co))
    FROM Sector s JOIN s.companies co
    GROUP BY s.sectorName
    ORDER BY COUNT(co) DESC
    """)
    List<TopSectorsByCompanyCountDTO> getTopSectorsByCompanyCount();
}