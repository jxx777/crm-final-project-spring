package itschool.crmfinalproject.users.repository;

import itschool.crmfinalproject.users.entity.Role;
import itschool.crmfinalproject.users.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleEnum role);
}
