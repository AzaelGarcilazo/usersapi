package compass.career.usersapi.repository;

import compass.career.usersapi.model.PasswordRecovery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecovery, Integer> {
    Optional<PasswordRecovery> findByTokenAndUsedFalse(String token);
}