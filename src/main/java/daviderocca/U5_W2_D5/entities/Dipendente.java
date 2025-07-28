package daviderocca.U5_W2_D5.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Dipendente {

    @Id
    private String username;

    private String nome;

    private String cognome;

    private String email;

    private String password;

    @Column(name = "url_immagine_profilo")
    private String urlImmagineProfilo;

    public Dipendente(String username, String nome, String cognome, String email, String urlImmagineProfilo, String password) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.urlImmagineProfilo = urlImmagineProfilo;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Dipendente{" +
                "username='" + username + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", urlImmagineProfilo='" + urlImmagineProfilo + '\'' +
                '}';
    }
}
