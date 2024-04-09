package itschool.crmfinalproject.sectors.entity;

import itschool.crmfinalproject.companies.entity.Company;
import itschool.crmfinalproject.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "sectors")
public class Sector extends BaseEntity {

    @Column(unique = true, nullable = false)
    @Size(max = 255)
    @NotBlank
    private String sectorName;

    @Column(nullable = false)
    @NotNull
    private Double marketCap;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Company> companies = new HashSet<>();

    @Column
    private Double growthRate;

    @Column(length = 1024)
    private String description;

    @Column(name= "averageReturnOnInvestment")
    private Double averageReturnOnInvestment;

    @Column
    private Integer averageCompanySize;

    @Column
    private Integer totalNumberOfEmployees;


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
