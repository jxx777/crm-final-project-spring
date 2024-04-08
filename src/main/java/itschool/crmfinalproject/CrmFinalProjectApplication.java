package itschool.crmfinalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class CrmFinalProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrmFinalProjectApplication.class, args);
    }
}