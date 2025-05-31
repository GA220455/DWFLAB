package sv.edu.udb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sv.edu.udb.repository.domain.SecurityUser;

import java.util.Optional;


public interface UserRepository extends JpaRepository<SecurityUser, Long> {
    SecurityUser findByUsername(final String username);
    Optional<SecurityUser> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}


