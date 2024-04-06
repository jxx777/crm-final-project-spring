package itschool.crmfinalproject.repository;

import itschool.crmfinalproject.entity.app.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisRepository extends JpaRepository<Contact, Long> {
    // Define your complex JPQL queries here for analysis
}