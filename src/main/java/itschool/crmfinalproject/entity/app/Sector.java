package itschool.crmfinalproject.entity.app;

import itschool.crmfinalproject.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "sectors")
public class Sector extends BaseEntity {

    @Column(unique=true)
    private String sectorName;

    private Double marketCap;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Company> companies = new HashSet<>();

    public void addCompany(Company company) {
        companies.add(company);
        company.setSector(this);
    }

    public void removeCompany(Company company) {
        companies.remove(company);
        company.setSector(null);
    }

    // More fields maybe
}
