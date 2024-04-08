package itschool.crmfinalproject.repository;


import itschool.crmfinalproject.entity.user.Role;
import itschool.crmfinalproject.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleEnum role);
}
