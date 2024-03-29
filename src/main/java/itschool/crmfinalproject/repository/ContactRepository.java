package itschool.crmfinalproject.repository;

import itschool.crmfinalproject.entity.app.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    //    Optional<Contact> getContactById(Long id);
    Optional<Contact> findByEmail(String email);
}