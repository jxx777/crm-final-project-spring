package itschool.crmfinalproject.repository;

import itschool.crmfinalproject.entity.app.Company;
import itschool.crmfinalproject.entity.app.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByEmail(String email);

    void deleteByCompany(Company company);
}