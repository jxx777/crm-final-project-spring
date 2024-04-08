package itschool.crmfinalproject.entity.user;

import itschool.crmfinalproject.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RoleEnum role; // For example, "USER", "EMPLOYEE", "ADMIN"

    public Role(final RoleEnum role) {
        this.role = role;
    }
}