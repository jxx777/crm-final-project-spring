package itschool.crmfinalproject.startup;

import itschool.crmfinalproject.entity.user.Role;
import itschool.crmfinalproject.entity.user.RoleEnum;
import itschool.crmfinalproject.repository.RoleRepository;
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
            if (roleRepository.findByName(roleEnum).isEmpty()) {
                Role role = new Role();
                role.setName(roleEnum);
                roleRepository.save(role);
            }
        });
    }
}