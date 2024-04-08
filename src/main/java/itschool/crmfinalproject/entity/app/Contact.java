package itschool.crmfinalproject.entity.app;

import io.swagger.v3.oas.annotations.media.Schema;
import itschool.crmfinalproject.entity.BaseEntity;
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

    @Column(name = "first_name", nullable = false)
    @Schema(description = "The first name of the contact.", example = "John")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Schema(description = "The last name of the contact.", example = "Doe")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @Schema(description = "The email address of the contact.", example = "john.doe@example.com")
    private String email;

    @Column(name = "phone_number")
    @Schema(description = "The phone number of the contact.", example = "+123456789")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @Schema(description = "The company to which the contact is associated.")
    private Company company;

    @Column(name = "position")
    @Schema(description = "The position or job title of the contact.", example = "Software Engineer")
    private String position;

    @Embedded
    @Schema(description = "The address fieldDetails of the contact.")
    private Address address;

    @Column(name = "description", length = 2048)
    @Schema(description = "A brief description of the contact.", example = "John is a key account manager.")
    private String description;

    @ElementCollection
    @CollectionTable(name = "contact_tags", joinColumns = @JoinColumn(name = "contact_id"))
    @Column(name = "tag")
    @Schema(description = "A set of tags associated with the contact.", example = "[\"VIP\", \"Avoid\", \"Priority\"]")
    private Set<String> tags;

    public Contact() {}


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