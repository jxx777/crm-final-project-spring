package itschool.crmfinalproject.contacts.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import itschool.crmfinalproject.common.entity.Address;
import itschool.crmfinalproject.common.entity.BaseEntity;
import itschool.crmfinalproject.companies.entity.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "contacts")
@Schema(description = "Represents a contact in the CRM application.")
public class Contact extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "position")
    private String position;

    @Embedded
    private Address address;

    @Column(name = "description", length = 2048)
    private String description;

    @ElementCollection
    @CollectionTable(name = "contact_tags", joinColumns = @JoinColumn(name = "contact_id"))
    @Column(name = "tag")
    private Set<String> tags;


    // Equals and HashCode considering 'id' field only for simplicity
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact contact)) return false;
        return Objects.equals(getId(), contact.getId());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}