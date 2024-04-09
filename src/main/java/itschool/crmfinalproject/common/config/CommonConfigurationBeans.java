package itschool.crmfinalproject.common.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to define common beans used across the application.
 */
@Configuration
public class CommonConfigurationBeans {

    /**
     * Provides a Faker instance for generating fake data.
     *
     * @return A Faker instance.
     */
    @Bean
    public Faker faker() {
        return new Faker();
    }
}