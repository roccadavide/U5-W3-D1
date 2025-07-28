package daviderocca.U5_W2_D5.services;

import daviderocca.U5_W2_D5.entities.Dipendente;
import daviderocca.U5_W2_D5.entities.Prenotazione;
import daviderocca.U5_W2_D5.entities.Viaggio;
import daviderocca.U5_W2_D5.exceptions.BadRequestException;
import daviderocca.U5_W2_D5.exceptions.NotFoundException;
import daviderocca.U5_W2_D5.payloads.NewPrenotazioneDTO;
import daviderocca.U5_W2_D5.repositories.PrenotazioneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private ViaggioService viaggioService;

    @Autowired
    private DipendenteService dipendenteService;

    public Page<Prenotazione> getPrenotazione(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione savePrenotazione(NewPrenotazioneDTO payload){

        Viaggio viaggioAssociato = viaggioService.findViaggioById(payload.idViaggio());
        Dipendente dipendenteAssociato = dipendenteService.findDipendenteByUsername(payload.usernameDipendente());

        Optional<Prenotazione> prenotazioneGiaEsistente = prenotazioneRepository
                .findByDipendenteAndViaggio_DataDiPartenza(dipendenteAssociato, viaggioAssociato.getDataDiPartenza());

        if(prenotazioneGiaEsistente.isPresent()) throw new BadRequestException("Hai gia una prenotazione per il " + viaggioAssociato.getDataDiPartenza());

        String noteDipendente = (payload.noteDipendente() == null || payload.noteDipendente().isBlank()) ?
                "Il dipendente non ha preferenze particolari!" : payload.noteDipendente();


        Prenotazione newPrenotazione = new Prenotazione();
        newPrenotazione.setDataRichiestaPrenotazione(payload.dataRichiestaPrenotazione());
        newPrenotazione.setNoteDipendente(noteDipendente);
        newPrenotazione.setViaggio(viaggioAssociato);
        newPrenotazione.setDipendente(dipendenteAssociato);


        Prenotazione savedPrenotazione = this.prenotazioneRepository.save(newPrenotazione);

        log.info("La prenotazione con ID: " + savedPrenotazione.getDataRichiestaPrenotazione() + " è stata correttamente salvata nel DB!");

        return savedPrenotazione;
    }


    public Prenotazione findPrenotazioneById(Long prenotazioneId) {
        return this.prenotazioneRepository.findById(prenotazioneId).orElseThrow(() -> new NotFoundException(prenotazioneId));
    }

    public Prenotazione findPrenotazioneByIdAndUpdate(Long prenotazioneId, NewPrenotazioneDTO payload) {
        Prenotazione found = findPrenotazioneById(prenotazioneId);

        Viaggio viaggioAssociato = viaggioService.findViaggioById(payload.idViaggio());
        Dipendente dipendenteAssociato = dipendenteService.findDipendenteByUsername(payload.usernameDipendente());

        Optional<Prenotazione> prenotazioneGiaEsistente = prenotazioneRepository
                .findByDipendenteAndViaggio_DataDiPartenza(dipendenteAssociato, viaggioAssociato.getDataDiPartenza());

        //Qui ho dovuto aggiungere due controlli per fare in modo che il dipendente non possa prenotare
        // per la stessa data a meno che non sia la prenotazione che sta modificando
        if(prenotazioneGiaEsistente.isPresent() && !(prenotazioneGiaEsistente.get().getId() == prenotazioneId))
            throw new BadRequestException("Hai gia una prenotazione per il " + viaggioAssociato.getDataDiPartenza());

        String noteDipendente = (payload.noteDipendente() == null || payload.noteDipendente().isBlank()) ?
                "Il dipendente non ha preferenze particolari!" : payload.noteDipendente();

        found.setDataRichiestaPrenotazione(payload.dataRichiestaPrenotazione());
        found.setNoteDipendente(noteDipendente);
        found.setViaggio(viaggioAssociato);
        found.setDipendente(dipendenteAssociato);

        Prenotazione modifiedPrenotazione = this.prenotazioneRepository.save(found);

        log.info("La prenotazione con ID: " + modifiedPrenotazione.getDataRichiestaPrenotazione() + " è stata modificata correttamente!");

        return modifiedPrenotazione;

    }

    public void findPrenotazioneByIdAndDelete(Long prenotazioneId) {
        Prenotazione found = findPrenotazioneById(prenotazioneId);
        this.prenotazioneRepository.delete(found);
        log.info("La prenotazione con ID: " + found.getDataRichiestaPrenotazione() + " è stata cancellata correttamente dal DB!");
    }

}
