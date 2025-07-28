package daviderocca.U5_W2_D5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "data_richiesta_prenotazione")
    private LocalDate dataRichiestaPrenotazione;

    @Column(name = "note_dipendente")
    private String noteDipendente;

    @ManyToOne
    @JoinColumn(name = "id_viaggio")
    private Viaggio viaggio;

    @ManyToOne
    @JoinColumn(name = "username_dipendente")
    private Dipendente dipendente;

    public Prenotazione(LocalDate dataRichiestaPrenotazione, String noteDipendente, Viaggio viaggio, Dipendente dipendente) {
        this.dataRichiestaPrenotazione = dataRichiestaPrenotazione;
        this.noteDipendente = noteDipendente;
        this.viaggio = viaggio;
        this.dipendente = dipendente;
    }

    @Override
    public String toString() {
        return "Prenotazione{" +
                "id=" + id +
                ", dataRichiestaPrenotazione=" + dataRichiestaPrenotazione +
                ", noteDipendente='" + noteDipendente + '\'' +
                ", viaggio=" + viaggio +
                ", dipendente=" + dipendente +
                '}';
    }
}
