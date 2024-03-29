package itschool.crmfinalproject.configuration;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfigurationBeans {

    @Bean
    public Faker faker() {
        return new Faker();
    }
}