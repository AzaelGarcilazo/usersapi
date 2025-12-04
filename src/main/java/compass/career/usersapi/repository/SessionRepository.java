package compass.career.usersapi.repository;

import compass.career.usersapi.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    Optional<Session> findByTokenAndActiveTrue(String token);
    Optional<Session> findByToken(String token);
}