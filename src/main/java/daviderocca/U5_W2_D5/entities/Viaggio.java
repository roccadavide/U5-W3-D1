package daviderocca.U5_W2_D5.entities;

import daviderocca.U5_W2_D5.enums.StatoViaggio;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Viaggio {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String destinazione;

    @Column(name = "dara_di_partenza")
    private LocalDate dataDiPartenza;

    @Column(name = "dara_di_ritorno")
    private LocalDate dataDiRitorno;

    @Column(name = "stato_viaggio")
    @Enumerated(EnumType.STRING)
    private StatoViaggio statoViaggio;

    @ManyToOne
    @JoinColumn(name = "username_dipendente")
    private Dipendente dipendente;


    public Viaggio(String destinazione, LocalDate dataDiPartenza, LocalDate dataDiRitorno, StatoViaggio statoViaggio, Dipendente dipendente) {
        this.destinazione = destinazione;
        this.dataDiPartenza = dataDiPartenza;
        this.dataDiRitorno = dataDiRitorno;
        this.statoViaggio = statoViaggio;
        this.dipendente = dipendente;
    }

    @Override
    public String toString() {
        return "Viaggio{" +
                "id=" + id +
                ", destinazione='" + destinazione + '\'' +
                ", dataDiPartenza=" + dataDiPartenza +
                ", dataDiRitorno=" + dataDiRitorno +
                ", statoViaggio=" + statoViaggio +
                ", dipendente=" + dipendente +
                '}';
    }
}
