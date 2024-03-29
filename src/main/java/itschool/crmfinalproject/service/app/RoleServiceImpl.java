package itschool.crmfinalproject.service.app;

import itschool.crmfinalproject.entity.user.Role;
import itschool.crmfinalproject.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private RoleHierarchy roleHierarchy;
    private final RoleRepository roleRepository;


    public ResponseEntity<?> getRoles() {
        List<Role> allRoles = roleRepository.findAll();
        return ResponseEntity.ok(allRoles);
    }

    public Collection<? extends GrantedAuthority> getEffectiveAuthorities(Set<Role> roles) {
        // Convert roles to authorities
        Set<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());

        // Resolve the effective authorities using the role hierarchy
        return roleHierarchy.getReachableGrantedAuthorities(authorities);
    }
}
