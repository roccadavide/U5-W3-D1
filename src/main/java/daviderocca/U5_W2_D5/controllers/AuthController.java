package daviderocca.U5_W2_D5.controllers;

import daviderocca.U5_W2_D5.entities.Dipendente;
import daviderocca.U5_W2_D5.exceptions.ValidationException;
import daviderocca.U5_W2_D5.payloads.LoginDTO;
import daviderocca.U5_W2_D5.payloads.LoginRespDTO;
import daviderocca.U5_W2_D5.payloads.NewDipendenteDTO;
import daviderocca.U5_W2_D5.payloads.NewDipendenteRespDTO;
import daviderocca.U5_W2_D5.services.AuthService;
import daviderocca.U5_W2_D5.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        String accessToken = authService.checkCredentialAndGenerateToken(body);
        return new LoginRespDTO(accessToken);
    }


   @PostMapping("/register")
   public NewDipendenteRespDTO save(@RequestBody @Validated NewDipendenteDTO payload, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        } else {
            Dipendente newDipendente = this.dipendenteService.saveDipendente(payload);
            return new NewDipendenteRespDTO(newDipendente.getUsername());
        }
   }


}
