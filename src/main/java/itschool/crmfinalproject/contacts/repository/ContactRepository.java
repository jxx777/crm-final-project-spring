package itschool.crmfinalproject.contacts.repository;

import itschool.crmfinalproject.companies.entity.Company;
import itschool.crmfinalproject.contacts.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByEmail(String email);

    void deleteByCompany(Company company);
}