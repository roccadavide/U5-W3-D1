package daviderocca.U5_W2_D5.services;
//
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
import daviderocca.U5_W2_D5.entities.Dipendente;
import daviderocca.U5_W2_D5.enums.TipoUtente;
import daviderocca.U5_W2_D5.exceptions.BadRequestException;
import daviderocca.U5_W2_D5.exceptions.NotFoundException;
import daviderocca.U5_W2_D5.payloads.NewDipendenteDTO;
import daviderocca.U5_W2_D5.repositories.DipendenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class DipendenteService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private PasswordEncoder bcrypt;

//    @Autowired
//    private Cloudinary imageUploader;

    public Page<Dipendente> getDipendente(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return dipendenteRepository.findAll(pageable);
    }

    public Dipendente saveDipendente(NewDipendenteDTO payload){

        if(dipendenteRepository.existsByUsername(payload.username())) throw new BadRequestException("Username già in uso! Scegline un altro!");
        if(dipendenteRepository.existsByEmail(payload.email())) throw new BadRequestException("Email già in uso! Scegline un altra!");

        Dipendente newDipendente = new Dipendente();
        newDipendente.setUsername(payload.username());
        newDipendente.setNome(payload.nome());
        newDipendente.setCognome(payload.cognome());
        newDipendente.setEmail(payload.email());
        newDipendente.setUrlImmagineProfilo(null);
        newDipendente.setPassword(bcrypt.encode(payload.password()));
        newDipendente.setTipoUtente(TipoUtente.USER);


        Dipendente savedDipendente = this.dipendenteRepository.save(newDipendente);

        log.info("Il dipendente con username: " + savedDipendente.getUsername() + " è stato correttamente salvato nel DB!");

        return savedDipendente;
    }


    public Dipendente findDipendenteByUsername(String username) {
        return this.dipendenteRepository.findById(username).orElseThrow(() -> new NotFoundException(username));
    }

    public Dipendente findDipendenteByEmail(String email) {
        return this.dipendenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(email));
    }

    public Dipendente findDipendenteByUsernameAndUpdate(String username, NewDipendenteDTO payload) {
        Dipendente found = findDipendenteByUsername(username);

        if(dipendenteRepository.existsByUsername(payload.username())) throw new BadRequestException("Username già in uso! Scegline un altro!");
        if(dipendenteRepository.existsByEmail(payload.email())) throw new BadRequestException("Email già in uso! Scegline un altra!");

        found.setUsername(payload.username());
        found.setNome(payload.nome());
        found.setCognome(payload.cognome());
        found.setEmail(payload.email());
        found.setUrlImmagineProfilo(null);

        Dipendente modifiedDipendente = this.dipendenteRepository.save(found);

        log.info("Il dipendente con username: " + modifiedDipendente.getUsername() + " è stato modificato correttamente!");

        return modifiedDipendente;

    }

    public void findDipendenteByUsernameAndDelete(String username) {
        Dipendente found = findDipendenteByUsername(username);
        this.dipendenteRepository.delete(found);
        log.info("Il dipendente con username: " + found.getUsername() + " è stato cancellato correttamente dal DB!");
    }


//    public void uploadAvatar(MultipartFile file, String username) {
//        try {
//            String url = (String) imageUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
//            Dipendente found = findDipendenteByUsername(username);
//            found.setUrlImmagineProfilo(url);
//            this.dipendenteRepository.save(found);
//        } catch (IOException e) {
//            throw new BadRequestException("Ci sono stati problemi nel salvataggio del file!");
//        }
//    }


}
