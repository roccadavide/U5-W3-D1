package daviderocca.U5_W2_D5.repositories;

import daviderocca.U5_W2_D5.entities.Dipendente;
import daviderocca.U5_W2_D5.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, Long> {
    Optional<Viaggio> findByDipendenteAndDataDiPartenza(Dipendente dipendente, LocalDate dataDiPartenza);
}
