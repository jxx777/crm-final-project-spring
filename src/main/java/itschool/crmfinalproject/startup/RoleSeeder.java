package itschool.crmfinalproject.startup;

import itschool.crmfinalproject.users.entity.Role;
import itschool.crmfinalproject.users.enums.RoleEnum;
import itschool.crmfinalproject.users.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;

@Component
@RequiredArgsConstructor
public class RoleSeeder {
    private final RoleRepository roleRepository;

    @PostConstruct
    @Transactional
    public void seedRolesTable() {
        EnumSet.allOf(RoleEnum.class).forEach(roleEnum -> {
            if (roleRepository.findByRole(roleEnum).isEmpty()) {
                Role role = new Role();
                role.setRole(roleEnum);
                roleRepository.save(role);
            }
        });
    }
}