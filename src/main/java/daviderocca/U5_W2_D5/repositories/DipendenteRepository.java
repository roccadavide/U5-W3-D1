package daviderocca.U5_W2_D5.repositories;

import daviderocca.U5_W2_D5.entities.Dipendente;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Dipendente> findByEmail(String email);
}
