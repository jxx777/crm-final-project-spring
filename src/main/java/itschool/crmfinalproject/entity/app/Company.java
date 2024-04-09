package itschool.crmfinalproject.entity.app;

import itschool.crmfinalproject.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company extends BaseEntity {

    @Column(name = "company_name", unique = true, nullable = false)
    private String name;

    @Column(name = "evaluation")
    private Double evaluation;

    @Column(name = "income_from_company")
    private Double incomeFromCompany;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contact> contacts = new HashSet<>();

    public Company() {}

    public void addContact(Contact contact) {
        contacts.add(contact);
        contact.setCompany(this);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
        contact.setCompany(null);
    }

    // Equals and HashCode considering 'id' field only for simplicity
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company company)) return false;
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}