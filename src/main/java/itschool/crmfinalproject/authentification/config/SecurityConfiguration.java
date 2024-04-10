package itschool.crmfinalproject.authentification.config;

import itschool.crmfinalproject.common.config.SpringSecurityAuditorAware;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * Configures security aspects for the application, including CORS, CSRF, session management, and authentication.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    // List of endpoints that do not require authentication
    private static final String[] WHITE_LIST_URL = {
            "/auth/**",
            "/export/csv",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/activation/**",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/**",
    };

    private final JwtAuthenticationFilter filter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Provides the {@link AuthenticationManager} for the application.
     *
     * @param builder The {@link AuthenticationConfiguration} used to build the {@link AuthenticationManager}.
     * @return The {@link AuthenticationManager} bean.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

    /**
     * Defines the security filter chain to apply to HTTP requests.
     *
     * @param http The {@link HttpSecurity} to configure.
     * @return The {@link SecurityFilterChain} bean.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        configureCsrf(http);
        configureCors(http);
        configureUrlAuthorization(http);
        configureSessionManagement(http);
        addAuthenticationProvider(http);
        addCustomAuthenticationFilter(http);
        return http.build();
    }

    /**
     * Disables CSRF protection to simplify the security configuration.
     *
     * @param http The {@link HttpSecurity} to configure.
     * @throws Exception if an error occurs during configuration.
     */
    private void configureCsrf(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
    }

    /**
     * Configures CORS settings to allow requests from specified origins.
     *
     * @param http The {@link HttpSecurity} to configure.
     * @throws Exception if an error occurs during configuration.
     */
    private void configureCors(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(request -> {
            var corsConfig = new CorsConfiguration();
            corsConfig.setAllowedOrigins(List.of("http://localhost:3000"));
            corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
            corsConfig.setAllowedHeaders(List.of("*"));
            corsConfig.setAllowCredentials(true);
            return corsConfig;
        }));
    }

    /**
     * Configures URL authorization settings to define which endpoints require authentication.
     *
     * @param http The {@link HttpSecurity} to configure.
     * @throws Exception if an error occurs during configuration.
     */
    private void configureUrlAuthorization(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(WHITE_LIST_URL).permitAll()
                .anyRequest().authenticated()
        );
    }

    /**
     * Defines a role hierarchy to establish a relationship between different roles.
     *
     * @return The {@link RoleHierarchy} bean.
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ADMIN > EMPLOYEE and EMPLOYEE > USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    /**
     * Configures session management to stateless, which is suitable for REST APIs.
     *
     * @param http The {@link HttpSecurity} to configure.
     * @throws Exception if an error occurs during configuration.
     */
    private void configureSessionManagement(HttpSecurity http) throws Exception {
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    /**
     * Adds a custom authentication provider to the security configuration.
     *
     * @param http The {@link HttpSecurity} to configure.
     */
    private void addAuthenticationProvider(HttpSecurity http) {
        http.authenticationProvider(authenticationProvider);
    }

    /**
     * Adds a custom JWT authentication filter before the default UsernamePasswordAuthenticationFilter.
     *
     * @param http The {@link HttpSecurity} to configure.
     */
    private void addCustomAuthenticationFilter(HttpSecurity http) {
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Provides a custom {@link AuditorAware} implementation to track who is creating or modifying entities.
     *
     * @return The {@link AuditorAware} bean.
     */
    @Bean
    @Primary
    public AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }
}