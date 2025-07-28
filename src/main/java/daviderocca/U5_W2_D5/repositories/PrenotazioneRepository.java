package daviderocca.U5_W2_D5.repositories;

import daviderocca.U5_W2_D5.entities.Dipendente;
import daviderocca.U5_W2_D5.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    Optional<Prenotazione> findByDipendenteAndViaggio_DataDiPartenza(Dipendente dipendente, LocalDate dataDiPartenza);
}
