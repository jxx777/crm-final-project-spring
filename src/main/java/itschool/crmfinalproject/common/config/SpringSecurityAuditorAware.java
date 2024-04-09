package itschool.crmfinalproject.common.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * AuditorAware implementation to capture the current authenticated user for auditing purposes.
 */
@Configuration
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    /**
     * Obtains the current auditor (authenticated user) from the Spring Security context.
     *
     * @return An {@link Optional} containing the username of the authenticated user, or a default value
     * for system or anonymous actions.
     */
    @Override
    public @NotNull Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("System");  // System or scheduled task
        } else if ("anonymousUser".equals(authentication.getPrincipal())) {
            return Optional.of("Self-registered");  // Self-registration or anonymous action
        }
        return Optional.ofNullable(authentication.getName());  // Authenticated user's username
    }
}