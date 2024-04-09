package itschool.crmfinalproject.repository;

import itschool.crmfinalproject.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Optional<User> findByUsername(String username);

    Optional<User> findByRegisterToken(String token);
}
