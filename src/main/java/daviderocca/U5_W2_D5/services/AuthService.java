package daviderocca.U5_W2_D5.services;

import daviderocca.U5_W2_D5.entities.Dipendente;
import daviderocca.U5_W2_D5.exceptions.UnauthorizedException;
import daviderocca.U5_W2_D5.payloads.LoginDTO;
import daviderocca.U5_W2_D5.tools.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private JWTTools jwtTools;

    public String checkCredentialAndGenerateToken(LoginDTO body) {
        Dipendente found = this.dipendenteService.findDipendenteByEmail(body.email());

        if(found.getPassword().equals(body.password())) {
            String accessToken = jwtTools.createToken(found);
            return accessToken;
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }

}
