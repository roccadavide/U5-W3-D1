package daviderocca.U5_W2_D5.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import daviderocca.U5_W2_D5.enums.TipoUtente;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"password", "authorities", "enabled", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
public class Dipendente implements UserDetails {

    @Id
    private String username;

    private String nome;

    private String cognome;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private TipoUtente tipoUtente;

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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.tipoUtente.name()));
    }
}
